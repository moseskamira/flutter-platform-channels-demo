import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:native_access/widgets/ringtone_card.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late Future<List<String>> _ringtoneFuture;

  Future<List<String>> _getRingtones() async {
    const channel = MethodChannel('ringtone_channel');
    final tones = await channel.invokeListMethod('getRingTones');
    return tones?.cast<String>() ?? [];
  }

  @override
  void initState() {
    super.initState();
    _ringtoneFuture = _getRingtones();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
        centerTitle: true,
      ),
      body: FutureBuilder<List<String>>(
        future: _ringtoneFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return Center(child: Text("Error: ${snapshot.error}"));
          }
          final ringTones = snapshot.data ?? [];
          return ListView.builder(
            padding: const EdgeInsets.symmetric(vertical: 10),
            itemCount: ringTones.length,
            itemBuilder: (context, index) {
              final ringTone = ringTones[index];
              return Padding(
                padding: const EdgeInsets.symmetric(
                  horizontal: 12,
                  vertical: 6,
                ),
                child: RingToneCard(ringTone: ringTone),
              );
            },
          );
        },
      ),
    );
  }
}
