alter table menu
    add index find_menu_idx
        (cafe_id, is_recommended desc, priority);

alter table menu_board
    add index
        find_menu_board_idx
        (cafe_id, priority);
