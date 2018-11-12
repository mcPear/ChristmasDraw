INSERT INTO PUBLIC.USER (ID, ABOUT, CHILDREN, EMAIL, FIRST_NAME, LANG, LAST_NAME, PREFERRED_USERNAME, VIRTUAL)
VALUES (nextval('id_user_seq'), 'Jestem porucznikiem, chorągwi pancernej księcia Jeremiego Wiśniowieckiego.', 2,
        'user1@gmail.com', 'Jan', 'pl', 'Skrzetuski', 'user1', FALSE),
  (nextval('id_user_seq'), 'Jestem rotmistrzem kaliskim ze Skrzetuszewa.', 0, 'user2@gmail.com', 'Stanisław', 'pl',
   'Skrzetuski', 'user2', FALSE),
  (nextval('id_user_seq'), 'Jestem wierny ojczyźnie. Wyjdę fortelem z każdej opresji, a i w bitwie dobrze staję!',
   0, 'user3@gmail.com', 'Onufry', 'pl', 'Zagłoba', 'user3', FALSE),
  (nextval('id_user_seq'), 'Jestem wnuczką Herakliusza Billewicza. Mieszkam w Wodoktach.', 0, 'user4@gmail.com', 'Ola',
   'pl', 'Billewiczówna', 'user4', FALSE),
  (nextval('id_user_seq'), 'Jestem pułkownikiem wojsk litewskich i Rzeczypospolitej. Lubię skubać armię wroga.', 1,
   'userV1@gmail.com', 'Andrzej', 'pl', 'Kmicic', 'userV1', TRUE);

INSERT INTO PUBLIC.GROUPX (ID, CALCULATED_CHILD_GIFT_VALUE, CHILD_GIFT_VALUE, COLLECTOR_CONTACT, COUNT_CHILDREN, DRAW_DATE,
                           GIFT_VALUE, IS_DRAWN, NAME, CURRENCY, MAILS_SENT)
VALUES (nextval('id_group_seq'), NULL, 100, '601 302 738', TRUE, '2018-12-01', 150, FALSE, 'rodzina', 'zł', FALSE),
  (nextval('id_group_seq'), NULL, 200, '603 392 721', FALSE, '2018-07-02', 300, TRUE, 'kompania', 'zł', FALSE);

INSERT INTO PUBLIC.MEMBERSHIP (ID, ACCEPTED, INCLUDE_IN_FUTURE_DRAW, INCLUDED_IN_LAST_DRAW, OWNS, DRAWN_USER_ID,
                               GROUP_ID, USER_ID)
VALUES
  (nextval('id_membership_seq'), TRUE, TRUE, NULL, TRUE, NULL, 1, 1),
  (nextval('id_membership_seq'), TRUE, TRUE, NULL, FALSE, NULL, 1, 2),
  (nextval('id_membership_seq'), NULL, FALSE, NULL, FALSE, NULL, 1, 3),
  (nextval('id_membership_seq'), TRUE, TRUE, NULL, FALSE, NULL, 1, 4),
  (nextval('id_membership_seq'), TRUE, TRUE, NULL, FALSE, 2, 2, 1),
  (nextval('id_membership_seq'), TRUE, TRUE, NULL, TRUE, 1, 2, 2),
  (nextval('id_membership_seq'), TRUE, TRUE, NULL, FALSE, NULL, 1, 5);

INSERT INTO GIFT_PART (ID, NAME, VALUE, MEMBERSHIP_ID)
VALUES
  (nextval('id_giftpart_seq'), 'kubek', 20, 1),
  (nextval('id_giftpart_seq'), 't-shirt', 60, 1),
  (nextval('id_giftpart_seq'), 'czekoladki', 20, 1),
  (nextval('id_giftpart_seq'), 'yerba', 45, 1),
  (nextval('id_giftpart_seq'), 'kubek', 20, 7),
  (nextval('id_giftpart_seq'), 't-shirt', 60, 5),
  (nextval('id_giftpart_seq'), 'plecak', 20, 2),
  (nextval('id_giftpart_seq'), 'yerba', 31, 2),
  (nextval('id_giftpart_seq'), 'poduszka', 20, 3),
  (nextval('id_giftpart_seq'), 't-shirt', 60, 4),
  (nextval('id_giftpart_seq'), 'bombonierka', 20, 5),
  (nextval('id_giftpart_seq'), 'herbata', 22, 5);


