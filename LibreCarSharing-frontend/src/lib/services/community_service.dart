import 'package:angular2/angular2.dart';
import 'dart:convert';
import 'dart:html';
import 'dart:async';

import 'package:LibreCarSharingFrontend/interfaces/community.dart'; // Import community model
import 'package:LibreCarSharingFrontend/implementation/community_impl.dart';
import 'package:json_object/json_object.dart'; // import community factory

@Injectable()
class CommunityService {
  /** Get all communities
   *
   */
  Future<List<Community>> getCommunities() {
    Completer completer = new Completer();

    List<Community> returnList = new List<Community>();
    HttpRequest
        .getString("../api/communities")
        .then((String responseText) {
      List<JsonObject> responseList = JSON.decode(responseText);
      //TODO: Das muss auch einfacher gehen!!!
      responseList.forEach((JsonObject jsonObject) {
        returnList.add(new CommunityImpl.fromJsonString(JSON.encode(jsonObject)));
      });
      completer.complete(returnList);
    }).catchError((n) {
      print("Error in getCommunities.");
      completer.complete(null);
    });
    return completer.future;
  }

  /**
   * Get all communities
   * @param: id The ID of a community
   **/
  Future<List<Community>> getUserCommunity(int userId) {
    Completer completer = new Completer();

    List<Community> returnList = new List<CommunityImpl>();
    HttpRequest
        .getString("../api/user/" + userId.toString() + "/community")
        .then((String responseText) {
      List<JsonObject> responseList = JSON.decode(responseText);
      //TODO: Das muss auch einfacher gehen!!!
      responseList.forEach((JsonObject jsonObject) {
        returnList.add(new CommunityImpl.fromJsonString(JSON.encode(jsonObject)));
      });
      completer.complete(returnList);
    }).catchError((n) {
      print("Error in getUserCommunity.");
      completer.complete(null);
    });
    return completer.future;
  }
}
