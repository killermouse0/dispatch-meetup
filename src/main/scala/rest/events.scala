package meetup.rest

import dispatch._

trait OpenEvents { self: Client =>
  case class OpenEventsBuilder(city: Option[String] = None,
                               country: Option[String] = None,
                               state: Option[String] = None,
                               zipVal: Option[String] = None,
                               ll: Option[(Float, Float)] = None,
                               textVal: Option[String] = None,
                               radiusVal: Option[Int] = None,
                               statusVal: Option[String] = None,
                               fieldextra: Option[Seq[String]] = None,
                               omits: Option[Seq[String]] = None,
                               onlys: Option[Seq[String]] = None)
       extends Client.Completion {

    def inCity(city: String, country: String, state: Option[String]) =
      copy(city = Some(city), country = Some(country), state = state)

    def zip(z: String) = copy(zipVal = Some(z))

    def latlon(lat: Float, lon: Float) = copy(ll = Some((lat, lon)))

    def text(txt: String) = copy(textVal = Some(txt))

    def radius(r: Int) = copy(radiusVal = Some(r))

    def status(s: String) = copy(statusVal = Some(s))

    def fields(fs: Seq[String]) = copy(fieldextra = Some(fs))

    def omit(fs: String*) = copy(omits = Some(fs))

    def only(fs: String*) = copy(onlys = Some(fs))

    override def request[T](handler: Client.Handler[T]) =
      apply(apiHost / "2" / "open_events" <<? pmap)(handler)

    private def pmap = Map.empty[String, String] ++ city.map(
      "city" -> _
    ) ++ country.map(
      "country" -> _
    ) ++ state.map(
      "state" -> _
    ) ++ zipVal.map(
      "zip" -> _
    ) ++ ll.map {
      case (lat, _) =>
        ("lat" -> lat.toString)
    } ++ ll.map {
      case (_, lon) =>
        ("lon" -> lon.toString)
    } ++ textVal.map(
      "text" -> _
    ) ++ radiusVal.map(
      "radius" -> _.toString
    ) ++ statusVal.map(
      "status" -> _
    ) ++ fieldextra.map(
      "fields" -> _.mkString(",")
    ) ++ omits.map(
      "omit" -> _.mkString(",")
    ) ++ onlys.map(
      "only" -> _.mkString(",")
    )
  }

  def openEvents = OpenEventsBuilder()
}


trait Events { self: Client =>
    case class EventsBuilder(events: Option[Seq[String]] = None,
                             groupIds: Option[Seq[Int]] = None,
                             groupurls: Option[Seq[String]] = None,
                             statuses: Option[Seq[String]] = None,
                             rsvpVal: Option[String] = None,
                             memberVal: Option[Int] = None,
                             venueVal: Option[Int] = None,
                             fieldextra: Option[Seq[String]] = None,
                             omits: Option[Seq[String]] = None,
                             onlys: Option[Seq[String]] = None)
       extends Client.Completion {

    def id(ids: String*) =
      copy(events = Some(ids))

    def group(ids: Int*) = copy(groupIds = Some(ids))

    def groupurl(urlnames: String*) = copy(groupurls = Some(urlnames))

    def status(s: String*) = copy(statuses = Some(s))

    def rsvp(r: String) = copy(rsvpVal = Some(r))

    def member(id: Int) = copy(memberVal = Some(id))

    def venue(id: Int) = copy(venueVal = Some(id))

    def fields(fs: Seq[String]) = copy(fieldextra = Some(fs))

    def omit(fs: String*) = copy(omits = Some(fs))

    def only(fs: String*) = copy(onlys = Some(fs))

    override def request[T](handler: Client.Handler[T]) =
      apply(apiHost / "2" / "events" <<? pmap)(handler)

    private def pmap = Map.empty[String, String] ++ events.map(
      "id" -> _.mkString(",")
    ) ++ groupIds.map(
      "group_id" -> _.mkString(",")
    ) ++ groupurls.map(
      "group_urlname" -> _.mkString(",")
    ) ++ statuses.map(
      "status" -> _.mkString(",")
    ) ++ rsvpVal.map(
      "rsvp" -> _
    ) ++ memberVal.map(
        ("member_id" -> _.toString)
    ) ++ venueVal.map(
      "venue_id" -> _.toString
    ) ++ fieldextra.map(
      "fields" -> _.mkString(",")
    ) ++ omits.map(
      "omit" -> _.mkString(",")
    ) ++ onlys.map(
      "only" -> _.mkString(",")
    )
  }

  def events = EventsBuilder()
}
