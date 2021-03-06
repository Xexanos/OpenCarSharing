import 'dart:async';

import 'package:angular2/angular2.dart';
import 'package:ng_bootstrap/ng_bootstrap.dart';
import 'package:LibreCarSharingFrontend/interfaces/community.dart';

// Import services
import 'package:LibreCarSharingFrontend/services/community_service.dart';

@Component(
  selector: 'dashboard',
  styleUrls: const ['dashboard_component.css'],
  templateUrl: 'dashboard_component.html',
  directives: const [
    BS_TABLE_DIRECTIVES,
  ],
)
class DashboardComponent implements OnInit {
  final CommunityService _communityService;
  int page = 1;

  List<Community> communities = [];

  DashboardComponent(this._communityService);

  @override
  Future<Null> ngOnInit() async {
    communities = await _communityService.getCommunities();
  }
}
