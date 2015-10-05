#version 120

// Gouraud shading vertex shader

// INCOMING DATA

uniform vec4 Position;
// Vertex location (in model space)
attribute vec4 vPosition;
 varying vec3 lPos,vPos,vNorm;
// Normal vector at vertex (in model space)
attribute vec3 vNormal;
attribute vec2 textureCoor;

varying vec2 texCoord;
// Model transformations
uniform vec3 theta;
uniform vec3 trans;
uniform vec3 scale;

// Camera parameters
uniform vec3 cPosition;
uniform vec3 cLookAt;
uniform vec3 cUp;

// View volume boundaries
uniform float left;
uniform float right;
uniform float top;
uniform float bottom;
uniform float near;
uniform float far;

uniform vec4 arrayAmbient;
uniform vec4 arrayDiffuseColor;
uniform vec4 specularColor;
uniform vec4 Color;
uniform vec4 ambiColor;

uniform float reflectionCoefficient;
uniform float diffuseReflectionCoefficient;
uniform float specularExponent;
uniform float specularReflectionCoefficient;



// OUTGOING DATA
varying vec4 colorG;

void main()
{
    // Compute the sines and cosines of each rotation about each axis
    vec3 angles = radians( theta );
    vec3 c = cos( angles );
    vec3 s = sin( angles );

    // Create rotation matrices
    mat4 rxMat = mat4( 1.0,  0.0,  0.0,  0.0,
                       0.0,  c.x,  s.x,  0.0,
                       0.0,  -s.x, c.x,  0.0,
                       0.0,  0.0,  0.0,  1.0 );

    mat4 ryMat = mat4( c.y,  0.0,  -s.y, 0.0,
                       0.0,  1.0,  0.0,  0.0,
                       s.y,  0.0,  c.y,  0.0,
                       0.0,  0.0,  0.0,  1.0 );

    mat4 rzMat = mat4( c.z,  s.z,  0.0,  0.0,
                       -s.z, c.z,  0.0,  0.0,
                       0.0,  0.0,  1.0,  0.0,
                       0.0,  0.0,  0.0,  1.0 );

    mat4 xlateMat = mat4( 1.0,     0.0,     0.0,     0.0,
                          0.0,     1.0,     0.0,     0.0,
                          0.0,     0.0,     1.0,     0.0,
                          trans.x, trans.y, trans.z, 1.0 );

    mat4 scaleMat = mat4( scale.x,  0.0,     0.0,     0.0,
                          0.0,      scale.y, 0.0,     0.0,
                          0.0,      0.0,     scale.z, 0.0,
                          0.0,      0.0,     0.0,     1.0 );

    // Create view matrix
    vec3 nVec = normalize( cPosition - cLookAt );
    vec3 uVec = normalize( cross (normalize(cUp), nVec) );
    vec3 vVec = normalize( cross (nVec, uVec) );

    mat4 viewMat = mat4( uVec.x, vVec.x, nVec.x, 0.0,
                         uVec.y, vVec.y, nVec.y, 0.0,
                         uVec.z, vVec.z, nVec.z, 0.0,
                         -1.0*(dot(uVec, cPosition)),
                         -1.0*(dot(vVec, cPosition)),
                         -1.0*(dot(nVec, cPosition)), 1.0 );

    // Create projection matrix
    mat4 projMat = mat4( (2.0*near)/(right-left), 0.0, 0.0, 0.0,
                         0.0, ((2.0*near)/(top-bottom)), 0.0, 0.0,
                         ((right+left)/(right-left)),
                         ((top+bottom)/(top-bottom)),
                         ((-1.0*(far+near)) / (far-near)), -1.0,
                         0.0, 0.0, ((-2.0*far*near)/(far-near)), 0.0 );

    
    // Transformation order:
    //    scale, rotate Z, rotate Y, rotate X, translate
    mat4 modelMat = xlateMat * rxMat * ryMat * rzMat * scaleMat;
    mat4 modelViewMat = viewMat * modelMat;
     
    // Transform the vertex location into clip space
    gl_Position =  projMat * viewMat  * modelMat * vPosition;
        
    vec4 eyeVertex = modelViewMat * vPosition;
    vec4 eyeLight = viewMat * Position;
    vec4  vnormal4 = vec4(vNormal,0.0);   
    vec4 eyeNormal = normalize(modelViewMat * vnormal4);

    vec3 L = normalize (eyeLight.xyz - eyeVertex.xyz);
    vec3 N = normalize (eyeNormal.xyz);

    // specular vectors
    vec3 R = normalize (reflect(L, N));
    vec3 V = normalize (eyeVertex.xyz);
    
    // calculate components
    vec4 diffuse = arrayDiffuseColor* Color * diffuseReflectionCoefficient * (dot(N, L));
    vec4 specular = Color * specularColor * specularReflectionCoefficient* pow(max(0.0, dot(R, V)),specularExponent);
    vec4 ambient= arrayAmbient* ambiColor* reflectionCoefficient;
   
    // set the final color
    colorG = ambient+diffuse + specular;
      texCoord = textureCoor;
}
