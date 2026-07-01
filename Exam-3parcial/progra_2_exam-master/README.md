## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


# Decisiones de tomadas

## 1. Identificador de `Student`

El repositorio declaraba `delete(int id)` pero `Student` no tenia ningun campo `id`.

**Decision:** se cambio la firma a `delete(String ci)`, usando la cedula de identidad como identificador natural.

**Motivo:** el `ci` ya es unico por validacion de negocio (`existsByCi` en `SaveStudentUseCase`), por lo que agregar un `id` tecnico habria sido redundante. Ademas, `Student` se declaro inmutable (`final` en todos sus campos, sin setters) para evitar que el identificador pueda mutarse desde fuera del dominio.

---

## 2. Control de acceso por rol

**Decision:** la logica de permisos se encapsulo en el propio enum `Role` mediante el metodo `canEditStudents()`:

```java
public boolean canEditStudents() {
    return this == ADMIN || this == CASHIER;
}
```

**Motivo:** evita tener `if (role == ADMIN)` repetidos en la vista o el presenter. La vista consulta el permiso una sola vez al inicializarse (`configurePermissions()`) y habilita o deshabilita los controles segun el resultado. Agregar o modificar un rol en el futuro implica cambiar unicamente el enum, sin tocar la vista.

## Bonus opcionales realizados.

Confirmacion antes de eliminar un estudiante.
Validacion de rango de grade con mensaje especifico.