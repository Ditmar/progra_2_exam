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
# dentificador de Student
Nos dejaba elegir entre agregar un id autogenerado al modelo o usar el ci que ya existía y yo me fui por el ci ya que un id autogenerado tiene su razón de ser cuando hay una base de datos real que lo maneja pero acá los datos viven en una lista en memoria así que agregar ese campo hubiera sido básicamente inventar un identificador de la nada el ci ya es único por estudiante y además es el dato que el usuario ve en la tabla así que lo usé como clave tanto en update como en delete a firma del repositorio quedó delete String ci y el repositorio mock simplemente busca al estudiante con ese ci y lo elimina.
# Control de acceso por rol
La idea era no llenar la vista de if (role == ADMIN) por todos lados, porque eso se vuelve un desastre cuando hay que cambiarlo lo que hice fue agregarle un método abstracto canEdit() directo al enum Role y cada constante lo implementa a su manera ADMIN devuelve true y tanto CASHIER como CLIENT devuelven false Con eso desde la vista no se necesit saber qué rol exacto tiene el usuario simplemente llamo currentUser.getRole().canEdit() y con ese booleano decido si muestro o escondo el formulario y los botones de guardar y eliminar l o bueno de hacerlo así es que si en algún momento se agrega un rol nuevo, solo hay que añadirlo al enum y definir su canEdit(), sin tener que tocar nada más.