# 🌐 Tresora Hotels

> **La mejor tecnología es la que no deja a nadie atrás**

---

## 📑 Índice

1. [¿Que es Tresora?](#-¿qué-es-Tresora?)
2. [Descripción del proyecto](#-descripción-del-proyecto)
3. [Tecnologías utilizadas](#-tecnologías-utilizadas)
4. [Cómo empezar](#-cómo-empezar)
5. [Capturas de pantalla](#-capturas-de-pantalla)
6. [Uso y funcionalidades](#-uso-y-funcionalidades)
7. [Estructura del proyecto](#-estructura-del-proyecto)
8. [Licencia](#-licencia)
9. [Autores](#-autores)
10. [Estado del proyecto](#-estado-del-proyecto)
11. [Demo online](#-demo-online)

---

## 🏨 ¿Qué es Tresora?

**Tresora Hotels** es una aplicación web desarrollada para facilitar la reserva de habitaciones en hoteles de forma ágil, intuitiva e inclusiva.

---

## 🧩 Descripción del proyecto

Una aplicación de reservas hoteleras que permite al usuario:

- Reservar habitaciones en distintos hoteles con fotos y desgloses de precio.
- Elegir entre reservas completas o rápidas.
- Añadir servicios extras a su reserva.
- Acceder a una experiencia accesible: incluye soporte por voz, etiquetas ARIA, tipografías accesibles, modo alto contraste, y tutoriales.

Además, cuenta con una parte de gestión para empleados y administradores con acceso a:

- Listas y formularios para gestionar habitaciones, usuarios, servicios, etc.
- Informes de limpieza.
- Gestión de reservas y facturación.

> El objetivo es diseñar una herramienta accesible, clara y funcional para mejorar el proceso de reservas tanto para el cliente como para el personal del hotel.

---

## 🛠 Tecnologías utilizadas

- **Backend:** Java, Spring Boot, Spring Web, Spring Security, Jakarta Validation
- **Frontend:** Thymeleaf, HTML5, CSS3
- **Base de datos:** PostgreSQL
- **Otros:** Maven, Docker

---

## 🚀 Cómo empezar

### 🧩 Requisitos previos

- Java 21
- Docker (para lanzar la base de datos)
- PostgreSQL (aunque no es obligatorio, ya que usamos un `DataLoader`)
- Maven

### 📦 Instalación y ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tuusuario/tresora-hotels.git
   cd tresora-hotels
   ```

2. Configura las credenciales de tu base de datos PostgreSQL en `application.properties`.

3. Inicia los servicios con Docker:
   ```bash
   docker-compose up
   ```

4. Ejecuta la aplicación (por ejemplo, desde tu IDE o usando Maven):
   ```bash
   mvn spring-boot:run
   ```

> Todos los datos iniciales se cargan automáticamente al iniciar gracias al `DataLoader`.

---

## 📸 Capturas de pantalla

> *Próximamente...*

---

## 🧪 Uso y funcionalidades

### 👤 Usuario

- Crear una cuenta
- Realizar reservas normales o rápidas sin registro
- Ver y descargar facturas pasadas
- Experiencia accesible: navegación con teclado, voz, contraste, etc.
- Formulario de contacto

### 🧑‍💼 Empleado

- Panel de gestión de habitaciones, reservas y usuarios
- Gestión de servicios e informes de limpieza

### 🛡 Roles

- **Usuario**
- **Empleado**
- **Administrador**

---

## 📁 Estructura del proyecto

```bash
src
├── main
│   ├── java/com/tresora
│   │   ├── controllers       # Controladores MVC
│   │   ├── entities          # Entidades JPA
│   │   ├── repositories      # Repositorios Spring Data
│   │   ├── services          # Servicios y lógica de negocio
│   │   ├── dto               # Clases de transferencia de datos
│   │   └── config            # Seguridad y configuración
│   └── resources
│       ├── templates         # Vistas Thymeleaf
│       ├── static            # Recursos estáticos (CSS, JS)
│       └── application.properties
```


---

## 📜 Licencia

Este proyecto está licenciado bajo la **GNU General Public License v3.0 (GPLv3)**.

Puedes:

- Usar, modificar y distribuir libremente.
- Publicar tus versiones modificadas siempre bajo GPLv3.
- Leer más 👉 [https://www.gnu.org/licenses/gpl-3.0.html](https://www.gnu.org/licenses/gpl-3.0.html)

---

## 👨‍💻 Autores

- [Jose A. Rodriguez](https://github.com/DoctoreJekyll)
- [Alba Gutierrez](https://github.com/AlbaGutierrezGarcia)
- [Alvaro Sevilla](https://github.com/alvarosevilla96)
- [Lucia Beltran](https://github.com/Lu-web165)
- [Natalia Garcia](https://github.com/natgarrod)

---

## 🚧 Estado del proyecto

🔧 En desarrollo

---

## 🌍 Demo online

👉 [https://grupo05-desarrollo.serverjava.net/](https://grupo05-desarrollo.serverjava.net/)

---
