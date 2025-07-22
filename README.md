#  ![Logo Tresora](src/main/resources/static/images/LogoTresoraFondoBlanco.png)

> ***La mejor tecnología es la que no deja a nadie atrás***



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

## 🏨🛎️ ¿Qué es Tresora? 

**Tresora Hotels** es una aplicación web desarrollada para facilitar la reserva de habitaciones en hoteles de forma ágil, intuitiva e inclusiva.

---

## 📝 Descripción del proyecto

Tresora es una cadena hotelera que permite al usuario:

- Reservar habitaciones en distintos hoteles con fotos y desgloses de precio.
- Elegir entre reservas convencionales o rápidas.
- Añadir servicios extras a su reserva.
- Acceder a una experiencia accesible: incluye soporte por voz, tutoriales, tipografía diseñada especialmente para personas con discapacidad visual, html con etiquetas ARIA y un diseño web con alto contraste *(WCAG AAA con ratio 9.89: 1 👉 https://webaim.org/resources/contrastchecker)*.

Además, cuenta con una parte de gestión para empleados y administradores con acceso a:

- Listas y formularios para gestionar habitaciones, usuarios, servicios, etc.
- Informes de limpieza.
- Gestión de reservas y facturación.


> **El objetivo es diseñar una herramienta accesible, clara y funcional para mejorar el proceso de reservas tanto para el cliente como para el personal del hotel.**

---

## 💻 Tecnologías utilizadas

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

### 🛠️ Instalación y ejecución 

**1. Clona el repositorio:**
   ```bash
   git clone https://github.com/tuusuario/tresora-hotels.git
   cd tresora-hotels
   ```

**2. Configura las credenciales de tu base de datos PostgreSQL en `application.properties`.**

**3. Inicia los servicios con Docker:**
   ```bash
   docker-compose up
   ```

**4. Ejecuta la aplicación (por ejemplo, desde tu IDE o usando Maven):**
   ```bash
   mvn spring-boot:run
   ```

> **Todos los datos iniciales se cargan automáticamente al iniciar gracias al ***`DataLoader`***.**

---

## 📸 Capturas de pantalla

> *Próximamente...*

---

## 🔎 Uso y funcionalidades

### 🙋🏻‍♂️🙋🏾‍♀️ Usuario

- Crear una cuenta
- Realizar reservas normales o rápidas sin registro
- Ver y descargar facturas pasadas
- Experiencia accesible: navegación con teclado, voz, contraste, etc.
- Formulario de contacto

### 🧑‍💼 Empleado

- Panel de gestión de habitaciones, reservas y usuarios
- Gestión de servicios e informes de limpieza

### 🔒🔑 Roles

- **Usuario**
- **Empleado**
- **Administrador**

---

## 📁 Estructura del proyecto 📂

```bash
src
├── main
│   ├── java/com/tresora
│   │   ├── config            # Seguridad y configuración
│   │   ├── controllers       # Controladores MVC
│   │   ├── DTOs              # Clases de transferencia de datos
│   │   ├── entities          # Entidades JPA
│   │   ├── loaders           # Carga de datos iniciales
│   │   ├── mappers           # Convertidores entidad-dto y viceversa
│   │   ├── repositories      # Repositorios Spring Data
│   │   ├── services          # Servicios y lógica de negocio
│   │   ├── util              # Clases utiles           
│   └── resources
│       ├── static                                 # Recursos estáticos
│       ├── templates                              # Vistas Thymeleaf
│       └── application.properties                 # Configuración inicial 
│       └── application-desarrollo.properties      # Configuración desarrollo
│       └── application-local.properties           # Configuración local
│       └── application-produccion.properties      # Configuración producción
```


---

## 📜 Licencia

Este proyecto está licenciado bajo la **GNU General Public License v3.0 (GPLv3)**.

Puedes:

- Ejecutar, adaptar y compartir el software sin restricciones.
- Publicar tus versiones modificadas siempre bajo GPLv3.
- Leer más 👉 [https://www.gnu.org/licenses/gpl-3.0.html](https://www.gnu.org/licenses/gpl-3.0.html)

***Compartir es vivir... ¡pero con licencia libre!***

---

## 👨🏾‍💻👩🏼‍💻 Autores
**Enlaces a GitHub :**
- [Jose A. Rodriguez](https://github.com/DoctoreJekyll) 
- [Alba Gutierrez](https://github.com/AlbaGutierrezGarcia)
- [Alvaro Sevilla](https://github.com/alvarosevilla96)
- [Lucia Beltran](https://github.com/Lu-web165)
- [Natalia Garcia](https://github.com/natgarrod)

**Enlaces a Linkedin :**
- [Jose A. Rodriguez](https://www.linkedin.com/in/jose-rodriguez-martin/)
- [Alba Gutierrez](https://www.linkedin.com/in/alba-gutiérrez-garcía/)
- [Alvaro Sevilla](https://www.linkedin.com/in/alvaro-fernandez-sevilla/)
- [Lucia Beltran](https://www.linkedin.com/in/lucía-beltrán-infante/)
- [Natalia Garcia](https://www.linkedin.com/in/natalia-garcia-rodriguez/)

---

## 🚧 Estado del proyecto

🔧  ***En desarrollo***  

---

## 🌍 Demo online

👉💙🌐 [https://grupo05-desarrollo.serverjava.net/](https://grupo05-desarrollo.serverjava.net/)

---
