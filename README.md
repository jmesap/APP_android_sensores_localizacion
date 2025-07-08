# Aplicación Android de Sensores Ambientales y Localización con Google Maps

Esta aplicación Android desarrollada en Java muestra datos en tiempo real de sensores ambientales (temperatura y humedad) y la ubicación actual del dispositivo en un mapa de Google Maps.

## Características

- Lectura en tiempo real de sensores de temperatura y humedad del dispositivo.
- Visualización de la ubicación actual en un mapa de Google Maps con marcador.
- Solicitud y manejo de permisos de ubicación en tiempo de ejecución.
- Interfaz sencilla y funcional basada en Android Studio y ViewBinding.

## Estructura del proyecto

- `MainActivity.java`: Actividad principal que gestiona sensores y mapa.
- Archivos de configuración Gradle (`build.gradle`, `settings.gradle`, `gradle.properties`).
- Uso de Google Play Services para ubicación y mapas.
- Layouts XML y recursos gestionados por ViewBinding.

## Requisitos

- Android Studio con SDK Android configurado.
- Dispositivo o emulador con sensores de temperatura y humedad (o mensajes de aviso si no están disponibles).
- Conexión a internet para cargar Google Maps.
- API Key de Google Maps configurada en el proyecto (no incluida en los archivos subidos).

## Configuración

1. Configurar la API Key de Google Maps en el archivo `google_maps_api.xml` (no incluido).
2. Compilar y ejecutar la aplicación en un dispositivo o emulador Android.
3. Conceder permisos de ubicación cuando se soliciten.

## Uso

- Al abrir la app, se muestran los valores actuales de temperatura y humedad (si el dispositivo tiene sensores).
- El mapa muestra la ubicación actual con un marcador y zoom adecuado.

## Tecnologías

- Java
- Android SDK
- Google Maps API
- Sensores Android (SensorManager)
- ViewBinding

## Autor

José Mesa Padilla
