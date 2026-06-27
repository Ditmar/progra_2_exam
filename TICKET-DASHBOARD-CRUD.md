| Campo | Valor |
|---|---|
| **Key** | PROGRACION 2 |
| **Tipo** | Feature / Story |
| **Componente** | `presentation.dashboard`, `data.repository`, `domain.usecase` |
| **Prioridad** | Alta |
| **Estimación** | 4 - 5 días |
| **Reporter** | Docente - Programación 2 |
| **Etiquetas** | `poo`, `interfaces`, `mvp`, `clean-architecture`, `evaluacion` |

# Completar el módulo Dashboard siguiendo la arquitectura MVP existente

## Descripción

El proyecto ya implementa el feature de **Login** siguiendo una arquitectura por capas (`domain` → `data` → `presentation`) y el patrón **MVP** mediante contratos (`LoginPresenterContract`, `LoginViewContract`). El feature de **Dashboard** quedó **incompleto**: hoy es una sola clase (`DashboardWindow`) que mezcla UI, datos hardcodeados y no respeta el patrón usado en el resto del sistema. El propio código lo marca:

```java
// TODO:
// This code needs to be refactor
// using the pattern MVP
public class DashboardWindow extends JFrame { ... }
```

Además existen dos problemas concretos que deben resolverse como parte de esta historia:

1. `LoginWindow.navigateToDashboard(User user)` recibe el usuario autenticado pero lo **ignora** y simplemente hace `new DashboardWindow()`. El `Role` del usuario nunca llega al Dashboard.
2. La interfaz `domain.repository.StudentRepository` ya declara `void delete(int id)`, pero `domain.model.Student` **no tiene ningún identificador** (`id`). Hay que decidir y justificar cómo resolver esta inconsistencia.

El objetivo de esta historia es que el módulo Dashboard quede construido con la **misma calidad arquitectónica y separación de responsabilidades** que el módulo Login, usando correctamente interfaces, inyección de dependencias y polimorfismo.

> No se entrega código de referencia para este feature. Se evalúa la capacidad de aplicar el patrón ya existente en `presentation.login` por analogía.

## Objetivo

Convertir el Dashboard en un módulo CRUD de estudiantes completo, desacoplado por capas, donde:
- La vista (`DashboardWindow`) no contenga lógica de negocio.
- El acceso a datos pase siempre por una interfaz de repositorio (no por datos hardcodeados).
- Las acciones disponibles dependan del `Role` del usuario autenticado.

## Tareas

### 1. Dominio (`domain`)
- [ ] Resolver la inconsistencia entre `StudentRepository.delete(int id)` y la ausencia de identificador en `Student`. Decidir entre: (a) agregar un campo `id` autogenerado al modelo, o (b) ajustar la firma del repositorio para identificar al estudiante por `ci`. Documentar brevemente la decisión y el motivo.
- [ ] Crear el/los caso(s) de uso necesarios en `domain.usecase` para listar, crear, actualizar y eliminar estudiantes (uno por operación, o una clase que agrupe las cuatro — a criterio del estudiante). Deben depender únicamente de la interfaz `StudentRepository`, inyectada por constructor, igual que hace `LoginUseCase` con `UserRepository`.
- [ ] Incluir validaciones de negocio en el/los caso(s) de uso (campos obligatorios, formato de `ci`, rango válido de `grade`, etc.), lanzando excepciones del mismo estilo que `LoginUseCase` (`IllegalArgumentException` con mensaje claro).

### 2. Capa de datos (`data.repository`)
- [ ] Implementar `MockStudentRepository implements StudentRepository`, con una colección en memoria como fuente de datos, siguiendo el mismo criterio que `MockUserRepository`.
- [ ] Migrar hacia este repositorio los datos de ejemplo que hoy están hardcodeados en `DashboardWindow.getData()`.
- [ ] Implementar correctamente `findAll`, `save`, `update` y `delete` sin duplicar ahí las validaciones de negocio (esas van en el caso de uso, no en el repositorio).

### 3. Contratos de presentación (`presentation.dashboard.contract`)
- [ ] Definir `DashboardPresenterContract`: las acciones que la vista puede solicitar al presenter (cargar estudiantes, guardar, actualizar, eliminar, etc.), siguiendo el estilo de `LoginPresenterContract`.
- [ ] Definir `DashboardViewContract`: las actualizaciones que el presenter puede pedirle a la vista (mostrar listado, mostrar error, mostrar éxito, limpiar formulario, etc.), siguiendo el estilo de `LoginViewContract`.
- [ ] Ambas deben ser interfaces puras (sin lógica), igual que sus equivalentes de Login.

### 4. Presenter (`presentation.dashboard`)
- [ ] Crear `HandlerDashboardWindow implements DashboardPresenterContract`, que reciba por constructor el/los caso(s) de uso (nunca el repositorio directamente) y se enlace a la vista mediante un método `attach(view)`, igual que `HandlerLoginWindow`.
- [ ] Toda la orquestación y manejo de errores debe vivir en este presenter, no en la vista.

### 5. Vista (`presentation.dashboard`)
- [ ] Refactorizar `DashboardWindow` para que implemente `DashboardViewContract` y reciba el presenter por constructor, eliminando el comentario `TODO` actual.
- [ ] Eliminar el método `getData()` hardcodeado: los datos deben llegar siempre a través del presenter.
- [ ] Agregar los controles de UI faltantes para completar el CRUD: formulario para alta/edición (puede reutilizar el componente `presentation.components.TextField`), botones de Guardar / Actualizar / Eliminar, y selección de fila en la tabla para editar o eliminar.
- [ ] Mantener consistencia visual con el resto de la app reutilizando `AppColors`, `AppFonts` y los componentes ya existentes en `presentation.components`.

### 6. Integración Login → Dashboard y control de acceso por rol
- [ ] Corregir `LoginWindow.navigateToDashboard(User user)` para que el usuario autenticado se propague realmente hasta el Dashboard (hoy se ignora el parámetro).
- [ ] Usar el `Role` del usuario autenticado para habilitar o restringir acciones del Dashboard (por ejemplo: solo ciertos roles pueden crear/editar/eliminar; el resto solo puede consultar el listado). La forma de resolverlo (estrategia con interfaz, comportamiento por enum, etc.) queda a criterio del estudiante, pero la lógica de permisos debe estar encapsulada y no repetida con `if (role == ...)` dispersos por toda la vista.

### 7. Composición (`App.java`)
- [ ] Actualizar el punto de ensamblado de la aplicación para instanciar `MockStudentRepository`, los casos de uso de estudiante y `HandlerDashboardWindow`, siguiendo el mismo estilo de ensamblado manual ya usado para Login.

## Criterios de aceptación

1. **Dado** un login exitoso con cualquier rol, **cuando** se abre el Dashboard, **entonces** la tabla muestra estudiantes obtenidos a través de `StudentRepository`, no de datos hardcodeados en la vista.
2. **Dado** un usuario con permisos de edición, **cuando** crea un nuevo estudiante desde el formulario, **entonces** aparece reflejado en la tabla sin reiniciar la aplicación.
3. **Dado** un usuario con permisos de edición, **cuando** selecciona un estudiante y modifica sus datos, **entonces** los cambios se reflejan en la tabla tras guardar.
4. **Dado** un usuario con permisos de edición, **cuando** elimina un estudiante seleccionado, **entonces** este desaparece de la tabla.
5. **Dado** un usuario sin permisos de edición (según `Role`), **cuando** abre el Dashboard, **entonces** las acciones de crear/editar/eliminar están deshabilitadas u ocultas, pero puede ver el listado.
6. **Dado** un dato inválido en el formulario (campo vacío, formato incorrecto), **cuando** se intenta guardar, **entonces** la vista muestra un mensaje de error proveniente del caso de uso, sin romper la aplicación.
7. Ninguna clase de `domain` o `data` importa o depende de clases de Swing (`JFrame`, `JTable`, `ActionListener`, etc.).
8. `DashboardWindow` y `HandlerDashboardWindow` se comunican exclusivamente a través de `DashboardViewContract` / `DashboardPresenterContract`.
9. El identificador usado por `StudentRepository.delete(...)` es consistente con el modelo `Student` (no quedan métodos que dependan de un campo inexistente).
10. El proyecto compila sin errores y el flujo de Login existente sigue funcionando sin regresiones.

## Definición de terminado (DoD)

- [ ] Compila sin errores en el entorno de VS Code Java del proyecto.
- [ ] Se respeta la convención de paquetes ya existente (`domain`, `data`, `infrastructure`, `presentation`).
- [ ] Demo manual: iniciar sesión con un rol con permisos de edición y con un rol sin permisos, mostrando la diferencia de capacidades en el Dashboard.
- [ ] Breve nota (comentario o sección en el README) explicando las decisiones de diseño tomadas: cómo se resolvió el identificador de `Student` y cómo se implementó el control de acceso por rol.

## Fuera de alcance

- Persistencia real en base de datos o archivo (la fuente de datos sigue siendo en memoria vía repositorio Mock).
- Pruebas unitarias automatizadas (puede proponerse como mejora opcional).
- Cambios al flujo de autenticación, registro o recuperación de contraseña.
- Diseño visual pixel-perfect: se evalúa arquitectura y POO, no estética.

## Qué se evalúa (rúbrica de POO e interfaces)

- Inversión de dependencias real: los casos de uso y el presenter dependen de **interfaces**, nunca de clases concretas (`MockStudentRepository`, `DashboardWindow`, etc.).
- Encapsulamiento correcto en el modelo y en las nuevas clases (sin exponer estado interno innecesario).
- Responsabilidad única por clase: la vista no valida reglas de negocio, el repositorio no las valida tampoco, el presenter no dibuja UI.
- Cohesión de los contratos: cada interfaz declara solo lo necesario para la colaboración View ⇄ Presenter.
- Manejo de errores con excepciones específicas de dominio, igual criterio que `LoginUseCase`.
- Uso de abstracción/polimorfismo para resolver el control de acceso por `Role`, evitando condicionales repetidos en la vista.

## Bonus (opcional, no requerido)

- Filtro/búsqueda de estudiantes por nombre o CI.
- Confirmación (`JOptionPane`) antes de eliminar un estudiante.
- Validación de rango de `grade` con mensaje específico.
- Tests unitarios de los casos de uso usando un repositorio falso (`fake`) que no dependa de Swing.

## Forma de entrega

1. Crear una rama propia a partir de `main` (por ejemplo `feature/dashboard-<nombre-apellido>`).
2. Subir los cambios a esa rama en el repositorio (fork o rama remota, según se indique en clase).
3. Abrir un **Pull Request** hacia `main` con los cambios propuestos.
4. En la descripción del PR incluir:
   - Resumen de la solución y decisiones de diseño tomadas (en particular, cómo se resolvió el identificador de `Student` y el control de acceso por `Role`).
   - Capturas o breve descripción del demo manual (login con rol con permisos y sin permisos).
5. El docente revisará el código y dejará comentarios directamente sobre el PR. No se evalúa código fuera de un PR abierto.
6. Evitar hacer *force push* sobre el PR una vez que el docente haya comenzado a comentar, salvo que se solicite explícitamente.
