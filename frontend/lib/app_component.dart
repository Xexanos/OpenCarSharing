import 'package:angular2/angular2.dart';
import 'package:angular_components/angular_components.dart';

import 'package:frontend/src/components/login/login_component.dart';

// AngularDart info: https://webdev.dartlang.org/angular
// Components info: https://webdev.dartlang.org/components

@Component(
  selector: 'my-app',
  styleUrls: const ['app_component.css'],
  templateUrl: 'app_component.html',
  directives: const [materialDirectives, LoginComponent],
  providers: const [materialProviders],
)
class AppComponent {
  String title = "LibreCarSharing";
}
