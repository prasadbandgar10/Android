import 'package:flutter/animation.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget{

  @override
  Widget build(BuildContext context){
    return MaterialApp(title: "Welcome to my first Application",
                       home: Scaffold(
                         appBar: AppBar(
                           title: Text("Welcome to my first application"),
                         ),
                         body: Center(
                           child: Text("Hello World!"),
                         ),
                       ),
    );

  }


}