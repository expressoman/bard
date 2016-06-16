
import com.gu.bard.models.FacebookPageConfig
import com.gu.cm.{ ConfigurationLoader, Identity }
import play.api.ApplicationLoader.Context
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.BuiltInComponentsFromContext
import play.api.routing.Router
import controllers._
import play.api.i18n.I18nComponents
import router.Routes

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

  val pages = mandatoryConfig("pages").split(",").toSeq

  val fbPageConfig: Map[String, FacebookPageConfig] = pages.map { p =>
    val page = p.trim
    val pageId = mandatoryConfig(s"fb.page.$page.id")
    val accessToken = mandatoryConfig(s"fb.page.$page.accesstoken")

    (page, FacebookPageConfig(page, pageId, accessToken))
  }.toMap

  val healthcheckController = new Healthcheck
  val appController = new Application(wsClient, configuration)
  val pageInsightsController = new PageInsightsApi(pages, fbPageConfig)
  val loginController = new Login(wsClient, configuration)

  val assets = new Assets(httpErrorHandler)
  val router: Router = new Routes(httpErrorHandler, appController, healthcheckController, pageInsightsController, loginController, assets)

}