Select ap.username, b.title from appUser as ap INNER JOIN userBook as ub ON ap.id = ub.user_id INNER JOIN book as b ON ub.book_id = b.book_id WHERE ap.id = 1
appuser