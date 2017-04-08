# Entity Lookup


When entities are parsed by ATSD or stored in ATSD, their names can be
looked up in the Entity Lookup mappings.

Entity lookup should contain one entry per line. The format is
`entity_name=new_entity_name`. The amount of lines is unlimited.

For example, if entity lookup mappings contain `entity001=sensor001` and
the entity being stored is named `entity001`, then the name will be saved in
ATSD as `sensor001`.

Entity lookup can be found on the bottom of the Entities Page:

![](images/entity_lookup_button.png "entity_lookup_button")

Entity lookup user interface:

![](images/entity_lookup_ui.png "entity_lookup_ui")
