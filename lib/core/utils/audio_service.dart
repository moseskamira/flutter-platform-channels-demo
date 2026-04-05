import 'package:just_audio/just_audio.dart';

class AudioService {
  static final AudioPlayer _player = AudioPlayer();
  static Future<void> play(String url) async {
    await _player.stop();
    await _player.setUrl(url);
    _player.play();
  }

  static Future<void> stop() async {
    await _player.stop();
  }
}
