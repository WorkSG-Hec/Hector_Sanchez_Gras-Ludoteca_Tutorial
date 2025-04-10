# Hector_SanchezGras-Ludoteca_Tutorial

En este tutorial creamos una aplicación utilizando `Angular para frontend` y `Spring Boot para backend`.

---

## Configuración del proyecto

### 1. Clonar el repositorio

Primero, clona este repositorio desde tu terminal, Git Bash o directamente desde tu IDE:

```bash
git clone https://github.com/WorkSG-Hec/Hector_Sanchez_Gras-Ludoteca_Tutorial.git
```

---

### 2. Instalar dependencias del frontend

Una vez clonado el proyecto, accede al directorio del frontend y ejecuta el siguiente comando para instalar las librerías necesarias:

```bash
cd tutorial-frontend-angular
npm install
```

> [!WARNING]
> Asegúrate de tener instalado Node.js y Angular CLI previamente.

---

## Inicio de la aplicación

### 1. Iniciar el backend (Spring Boot)

Desde la raíz del proyecto, ve al directorio del backend y ejecuta:

```bash
cd tutorial-backend-springboot
mvnw spring-boot:run
```

> Esto levantará un servidor local en `http://localhost:8080`.

---

### 2. Iniciar el frontend (Angular)

En una nueva consola (o terminal adicional en tu IDE), ve al directorio del frontend y ejecuta:

```bash
cd tutorial-frontend-angular
ng serve -o
```

Esto abrirá automáticamente la aplicación Angular en tu navegador en `http://localhost:4200`.

> [!TIP]
> Si usas un IDE con consola integrada, asegúrate de abrir una terminal diferente para cada uno (backend y frontend).

---

## ¡Listo!

Ya puedes comenzar a explorar el proyecto, navegar por la aplicación y revisar el código tanto del frontend como del backend.