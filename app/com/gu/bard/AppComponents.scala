
import com.gu.bard.Application
import com.gu.bard.api.PageInsightsApi
import com.gu.bard.models.FacebookPageConfig
import com.gu.cm.{ ConfigurationLoader, Identity }
import play.api.ApplicationLoader.Context
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.BuiltInComponentsFromContext
import play.api.routing.Router
import router.Routes
import controllers.Assets

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context) with AhcWSComponents {

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

  val appController = new Application
  val pageInsightsController = new PageInsightsApi(pages, fbPageConfig)

  val assets = new Assets(httpErrorHandler)
  val router: Router = new Routes(httpErrorHandler, appController, pageInsightsController, assets)

}