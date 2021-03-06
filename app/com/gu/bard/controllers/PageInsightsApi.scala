package controllers

import com.gu.bard.models.{ ErrorResponse, _ }
import com.gu.bard.services.PageService
import com.gu.bard.settings.PageInsightsPageSettings
import org.scalactic.{ Bad, Good }
import play.api.libs.json.Json
import play.api.mvc.{ Action, Controller }

class PageInsightsApi(pageNames: Seq[String], fbPageConfig: Map[String, FacebookPageConfig]) extends Controller {

  def getPages = Action {
    val pages = pageNames.flatMap { name =>
      PageInsightsPageSettings.config.get(name).map(settings => PageName(name, settings.prettyPageName))
    }

    Ok(Json.toJson(FacebookPages(pages)))
  }

  def getpageInsights(pageName: String, from: String, to: String) = Action {

    DateParameters(from, to) match {
      case Good(dp: DateParameters) =>

        (for {
          config <- fbPageConfig.get(pageName)
          page <- PageService.getPageInsightsPage(dp, config)
        } yield Ok(Json.toJson(page)))
          .getOrElse {
            NotFound(s"Could not retrieve page insights for page: $pageName")
          }

      case Bad(error) => BadRequest(Json.toJson(ErrorResponse(error.message)))

    }

  }

}
