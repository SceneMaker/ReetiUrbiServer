# ReetiUrbiServer
The project is used for: 1, Receive motor commands from VSM, then send corresponding urbi commands to reeti. 2, Receive cerevoice commands from VSM, then generate voice from Reeti side, move Reeti's mouth and send feedback to VSM.

How to compile :

open this project in netbeans(Ensure that the Java Platform is JDK 1.8).
open "ReetiUrbiServer" properties, choose "Library".
  add liburbijava.jar from directory "/usr/local/gostai/share/sdk-remote/java/lib". 
  add CereproTtsServer.jar from directory "ReetiUrbiServer/dist/lib".
  if you need to use CerepeocTts, Add cerevoice_eng.jar If you do not have cerevoice_eng.jar, please go to                        https://www.cereproc.com/de/products/sdk.
build

How to launch(The project should be launched on Reeti):

file: ./res/serverConfig.properties
  if you do not use CerepeocTts, change the value of CereproTts_Used to false.
  if you need to use CerepeocTts,
    change the value of CereproTts_Used to true.
    change the value of cereproc_lib_path to the directory of javalib. it should be (your cerevoice_sdk folder got from             www.cereproc.com )/cerevoice_eng/javalib/
    change the value of cereproc_voice to the directory of your voce file (eg 'cerevoice_heather_3.0.9_22k.voice').
    change the value of cereproc_license to the directory of your license file (eg 'license.lic').
    
Port

the port used in the project is 1256
