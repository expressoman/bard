
import com.gu.bard.Application
import com.gu.cm.{ ConfigurationLoader, Identity }
import play.api.ApplicationLoader.Context
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.BuiltInComponentsFromContext
import play.api.routing.Router
import controllers.Assets
import router.Routes

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context) with AhcWSComponents {

  val identity = {
    import com.gu.cm.PlayImplicits._
    Identity.whoAmI("bard", context.environment.mode)
  }

  override lazy val configuration = context.initialConfiguration ++ ConfigurationLoader.playConfig(identity, context.environment.mode)

  def mandatoryConfig(key: String): String = configuration.getString(key).getOrElse(sys.error(s"Missing config key: $key"))

  val appController = new Application

  val assets = new Assets(httpErrorHandler)
  val router: Router = new Routes(httpErrorHandler, appController, assets)

}