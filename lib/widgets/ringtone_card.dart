import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../core/utils/audio_service.dart';

class RingToneCard extends StatelessWidget {
  final String ringTone;

  const RingToneCard({super.key, required this.ringTone});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withValues(alpha: 0.05),
            blurRadius: 10,
            offset: const Offset(0, 4),
          ),
        ],
      ),
      child: ListTile(
        onTap: () async {
          const channel = MethodChannel('flutter_channel');
          await channel.invokeMethod('handleRingTone', {'name': ringTone});
        },
        leading: Container(
          padding: const EdgeInsets.all(10),
          decoration: BoxDecoration(
            color: Colors.blue.withValues(alpha: 0.1),
            borderRadius: BorderRadius.circular(12),
          ),
          child: const Icon(Icons.music_note, color: Colors.blue),
        ),
        title: Text(
          ringTone,
          style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
        ),
        subtitle: const Text("System ringtone", style: TextStyle(fontSize: 12)),
        trailing: IconButton(
          icon: const Icon(Icons.play_arrow_rounded),
          onPressed: () async {
            await AudioService.play(ringTone);
          },
        ),
      ),
    );
  }
}
