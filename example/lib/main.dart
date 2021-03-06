import 'package:flutter/material.dart';
import 'package:hi_health/hi_health.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  int distance = 0;
  int step = 0;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              FlatButton(
                onPressed: HiHealth.authorizeHuawei,
                child: Text('Authorize User'),
              ),
              Text('Step: $step'),
              FlatButton(
                onPressed: () async {
                  final start = DateTime.now().subtract(Duration(days: 5));
                  final end = DateTime.now().add(Duration(days:1));
                  final steps = await HiHealth.getSteps(start, end);
                  if (steps.isNotEmpty) {
                    setState(() {
                      step = steps[0].data;
                    });
                  }
                },
                child: Text('Get Steps'),
              ),
              Text('Distance: $distance'),
              FlatButton(
                onPressed: () async {
                  final start = DateTime.now().subtract(Duration(days: 5));
                  final end = DateTime.now();
                  final distances = await HiHealth.getDistance(start, end);
                  if (distances.isNotEmpty) {
                    setState(() {
                      distance = distances[0].data;
                    });
                  }
                },
                child: Text('Get Distance'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
