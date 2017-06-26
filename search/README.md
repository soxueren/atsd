# Search

## Overview

The search interface allows finding series records by matching the user-defined keywords against the following entity, metric, and tag fields:

* Entity name
* Entity label
* Metric name
* Metric label
* Series tag names
* Series tag values
* Entity tag names
* Entity tag values
* Metric tag names
* Metric tag values

## Syntax

A search query consists of terms and boolean operators. There are two types of terms: keywords and phrases.

A keyword term is a single word such as `location` or `nur`.

A phrase is a group of words surrounded by double quotes such as `location nur`.

Multiple terms can be combined together using boolean operators to form a more complex query.

A keyword may be prefixed with a field name to narrow the scope of the search to the particular series property, for example `entity:nurswgvml007`.

If the field is not specified, the search is performed in all fields.

### Fields

| **Field** | **Description** | **Example** |
|---|---|---|
| entity | Entity name. | `entity:nurswgvml007` |
| entity.label | Entity label. | `entity.label:nur*007` |
| metric | Metric name. | `metric:mpstat.cpu_busy` |
| metric.label | Metric label. | `metric.label:"cpu busy"` |
| {tag.name} | Series/metric/entity tag name. | `location:nur` |
| contents | All fields. | `nurswgvml007` or `contents:nurswgvml007` |

### Operators

To combine multiple terms, use boolean operators `AND`, `OR`, and `NOT`. The operators must be specified in upper case.

```ls
entity:nurswgvml007* AND mount_point:\/opt
```

The default operator applied to combine multiple keyword is `OR`. The following expressions are equivalent:

```ls
location OR nur
location nur
```

| **Operator** | **Description** | **Example** |
|---|---|---|
| `AND` | Both conditions must be satisfied. | `location AND nur` |
| `OR` | One of the conditions must be satisfied. | `location OR nur` |
| `NOT` | The condition must not be satisfied. | `location NOT nur` |

> The expression must not start with `NOT` operator.

### Wildcards

The keywords support single and multiple character wildcards.

* "*" symbol matches multiple characters.

```ls
he*
```

 * "?" symbol matches one character.

```ls
h?llo
```

> The wildcards can be used at the end or in the middle of a keyword.

### Reserved Characters

The following characters are reserved: `+ - && || ! ( ) { } [ ] ^ " ~ * ? : \ /`.

To escape the reserved characters use double-quotes or backslash:

```ls
mount_point:"/opt"
```

```ls
mount_point:\/opt
```

### Case Sensitivity

Search is case-insensitive.

## Examples

```ls
entity:nurswgvml007     /* Search for 'nurswgvml007' entity */

entity:nurswgvml*     /* Search for entities starting with 'nurswgvml' */

entity:nur*007     /* Search for entities starting with 'nur' and ending with '007' */

metric:mpstat.cpu_busy     /* Search for 'mpstat.cpu_busy' metric */

metric:mpstat.cpu*     /* Search for metrics starting with 'mpstat.cpu' */

metric:mpstat.*cpu*     /* Search for metrics starting with 'mpstat.' and containing 'cpu' */

location     /* Search for any field name or value containing the keyword 'location' */

location*     /* Search for any field name or value starting with 'location' */

location baltimore     /* Search for any field name or value containing keywords 'location' or 'Baltimore' */

location AND baltimore     /* Search for any field name or value containing both keywords 'location' and 'baltimore' */

location:baltimore     /* Search for series with series tag, metric tag or entity tag named 'location' containing the keyword 'baltimore' */

location:balt*     /* Search for series with series tag, metric tag or entity tag named 'location' starting with 'balt' */
```

### Scheduling

The index containing search terms is refreshed on schedule and may not reflect the changes such as new series and modifications of existing series that occurred since the last refresh.

The administrators can refresh the index manually on the **Admin: Diagnostics: Search Index** page as well as to customize the `search.indexing.schedule` setting on **Admin: Server Properties** page.
