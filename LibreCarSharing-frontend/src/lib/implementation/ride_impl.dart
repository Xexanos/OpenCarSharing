import 'package:json_object/json_object.dart';

import 'package:LibreCarSharingFrontend/interfaces/ride.dart';


class RideImpl extends JsonObject implements Ride {
  /**
   * This factory creates a car object from a JSON string
   */
  factory RideImpl.fromJsonString(String jsonString){
    return new JsonObject.fromJsonString(jsonString, new RideImpl());
  }

  RideImpl({int id, String name, int start, int end, bool changeable}) {
    this.id = id;
    this.name = name;
    this.start = start;
    this.end = end;
    this.changeable = changeable;
  }
}