import 'package:flutter/services.dart';

class LocationService {
  static const _channel = EventChannel('location_channel');

  static Stream<dynamic> getLocationStream() {
    return _channel.receiveBroadcastStream();
  }
}
