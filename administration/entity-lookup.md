Entity Lookup
=============

When entities are parsed by ATSD or stored in ATSD their names can be
looked up in the Entity Lookup mappings.

Entity lookup should contain one entry per line. Format is
`entity_name=new_entity_name`. Amount of lines is unlimited.

For example if Entity Lookup mappings contain `entity001=sensor001` and
the entity being stored is named `entity001` then it will be saved in
ATSD as `sensor001`.

Entity lookup can be found on the bottom of the Entities Page:

![](images/entity_lookup_button.png "entity_lookup_button")

Entity lookup UI:

![](images/entity_lookup_ui.png "entity_lookup_ui")