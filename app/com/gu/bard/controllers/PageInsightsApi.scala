package controllers

import com.gu.bard.models.{ ErrorResponse, _ }
import com.gu.bard.services.PageService
import org.scalactic.{ Bad, Good }
import play.api.libs.json.Json
import play.api.mvc.{ Action, Controller }

class PageInsightsApi(pages: Seq[String], fbPageConfig: Map[String, FacebookPageConfig]) extends Controller {

  def getPages = Action {
    Ok(Json.toJson(FacebookPages(pages)))
  }

  // TODO - This is pretty much the same now as in PostGraphs and can probably be extracted into Graphing.
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
