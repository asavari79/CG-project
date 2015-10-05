#version 120

// Gouraud shading fragment shader

varying vec4 colorG;
uniform int callType;

uniform sampler2D texture;
varying vec2 texCoord;
void main()
{
 
if(callType == 1 || callType ==2 ||callType ==3||callType ==4) {
    gl_FragColor =  texture2D(texture, texCoord) ;
   }
   
   else if (callType == 5) {
   
   gl_FragColor =colorG;
   }

}
