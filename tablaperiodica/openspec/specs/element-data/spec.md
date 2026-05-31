## ADDED Requirements

### Requirement: Dataset completo de 118 elementos
La aplicación SHALL incluir datos de los 118 elementos reconocidos por la IUPAC, sin requerir llamadas a red.

#### Scenario: Disponibilidad offline
- **WHEN** el usuario abre la aplicación sin conexión a internet
- **THEN** todos los datos de los 118 elementos están disponibles y la aplicación funciona completamente

#### Scenario: Cobertura completa
- **WHEN** se verifica el dataset
- **THEN** existen exactamente 118 entradas, con números atómicos del 1 al 118 sin huecos

### Requirement: Estructura de datos por elemento
Cada elemento en el dataset SHALL contener los siguientes campos: `atomicNumber` (entero), `symbol` (string), `name` (string, en español), `atomicMass` (número o string), `category` (string), `period` (entero), `group` (entero o null para lantánidos/actínidos), `electronConfiguration` (string), `standardState` (string: "Solid" | "Liquid" | "Gas" | "Unknown"), `meltingPoint` (número o null, en °C), `boilingPoint` (número o null, en °C), `summary` (string, descripción breve).

#### Scenario: Campos obligatorios presentes
- **WHEN** se accede a los datos de cualquier elemento
- **THEN** los campos `atomicNumber`, `symbol`, `name`, `category`, `period` y `group` tienen valores no nulos

#### Scenario: Campos opcionales con valor null explícito
- **WHEN** un elemento no tiene punto de fusión/ebullición conocido (e.g., elementos sintéticos pesados)
- **THEN** los campos `meltingPoint` y `boilingPoint` tienen valor `null` (no undefined ni string vacío)

### Requirement: Categorías de elementos normalizadas
El campo `category` SHALL usar exclusivamente uno de los siguientes valores normalizados: `"metal-alcalino"`, `"metal-alcalino-terreo"`, `"metal-transicion"`, `"metal-post-transicion"`, `"metaloide"`, `"no-metal"`, `"halogeno"`, `"gas-noble"`, `"lantanido"`, `"actinido"`, `"desconocido"`.

#### Scenario: Categoría válida en todos los elementos
- **WHEN** se itera sobre el dataset completo
- **THEN** el campo `category` de cada elemento es uno de los valores normalizados definidos
