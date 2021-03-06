#version 120
 
// Flat shading fragment shader
uniform vec4 arrayAmbient;
uniform vec4 arrayDiffuseColor;
uniform vec4 specularColor;
uniform vec4 Color;
uniform vec4 ambiColor;

uniform float reflectionCoefficient;
uniform float diffuseReflectionCoefficient;
uniform float specularExponent;
uniform float specularReflectionCoefficient;

varying vec4 eyeLight;
varying vec4 eyeVertex;
varying vec4 eyeNormal;

uniform int callType;

uniform sampler2D texture;
varying vec2 texCoord;

void main()
{        
    // calculate your vectors
    vec3 L = normalize (eyeLight.xyz - eyeVertex.xyz);
    vec3 N = normalize (eyeNormal.xyz);

    // specular vectors
    vec3 R = normalize (reflect(L, N));
    vec3 V = normalize (eyeVertex.xyz);
    
    // calculate components
    vec4 diffuse = arrayDiffuseColor* Color * diffuseReflectionCoefficient * (dot(N, L));
    //vec4 specular = Color * specularColor * specularReflectionCoefficient* pow(max(0.0, dot(R, V)),specularExponent);
    vec4 ambient= arrayAmbient* ambiColor* reflectionCoefficient;
   
    // set the final color
   
   if(callType == 1 || callType ==2 ||callType ==3||callType ==4) {
    gl_FragColor =  texture2D(texture, texCoord) ;
   }
   
   else if (callType == 5) {
   
   gl_FragColor =(ambient + diffuse);
   }
   

}

