
import com.gu.bard.api.{ Application, Auth, PageInsightsApi }
import com.gu.bard.models.FacebookPageConfig
import com.gu.cm.{ ConfigurationLoader, Identity }
import com.gu.googleauth.GoogleAuthConfig
import play.api.ApplicationLoader.Context
import router.Routes
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.BuiltInComponentsFromContext
import play.api.routing.Router
import controllers.Assets
import org.joda.time.Duration
import play.api.i18n.I18nComponents

class AppComponents(context: Context)
    extends BuiltInComponentsFromContext(context)
    with AhcWSComponents
    with I18nComponents {

  val identity = {
    import com.gu.cm.PlayImplicits._
    Identity.whoAmI("bard", context.environment.mode)
  }

  override lazy val configuration = context.initialConfiguration ++ ConfigurationLoader.playConfig(identity, context.environment.mode)

  def mandatoryConfig(key: String): String = configuration.getString(key).getOrElse(sys.error(s"Missing config key: $key"))

  val googleAuthConfig = GoogleAuthConfig(
    clientId = mandatoryConfig("google.clientId"),
    clientSecret = mandatoryConfig("google.clientSecret"),
    redirectUrl = mandatoryConfig("google.redirectUrl"),
    domain = Some("guardian.co.uk"),
    maxAuthAge = Some(Duration.standardDays(90)),
    enforceValidity = true
  )

  val pages = mandatoryConfig("pages").split(",").toSeq

  val fbPageConfig: Map[String, FacebookPageConfig] = pages.map { p =>
    val page = p.trim
    val pageId = mandatoryConfig(s"fb.page.$page.id")
    val accessToken = mandatoryConfig(s"fb.page.$page.accesstoken")

    (page, FacebookPageConfig(page, pageId, accessToken))
  }.toMap

  val appController = new Application(googleAuthConfig)
  val pageInsightsController = new PageInsightsApi(pages, fbPageConfig)
  val authController = new Auth(googleAuthConfig)(wsClient)

  val assets = new Assets(httpErrorHandler)
  val router: Router = new Routes(httpErrorHandler, appController, pageInsightsController, authController, assets)

}