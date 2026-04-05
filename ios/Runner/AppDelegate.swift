import Flutter
import UIKit

@main
@objc class AppDelegate: FlutterAppDelegate {
private let CHANNEL = "flutter_channel"

  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
   let controller = window?.rootViewController as! FlutterViewController
     let channel = FlutterMethodChannel(
         name: CHANNEL,
         binaryMessenger: controller.binaryMessenger
       )
    channel.setMethodCallHandler { (call, result) in
      switch call.method {
      case "getRingTones":
        let tones = self.getRingTones()
        result(tones)
      case "handleRingTone":
        if let args = call.arguments as? [String: Any],
           let name = args["name"] as? String {
          self.handleSelectedRingtone(name: name)
          result("Received: \(name)")
        } else {
          result(FlutterError(code: "NULL_NAME",
                              message: "Ringtone name is null",
                              details: nil))
        }

      default:
        result(FlutterMethodNotImplemented)
      }
    }
    GeneratedPluginRegistrant.register(with: self)
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }

    private func getRingTones() -> [String] {
      return [
        "Tone 1",
        "Tone 2",
        "Tone 3",
        "Tone 4"
      ]
    }

    private func handleSelectedRingtone(name: String) {
      UserDefaults.standard.set(name, forKey: "selected_ringtone")
      print("Saved Ringtone: \(name)")
    }


}
