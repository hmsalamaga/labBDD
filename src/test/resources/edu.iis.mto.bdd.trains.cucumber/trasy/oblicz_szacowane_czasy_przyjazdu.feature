# language: pl
Funkcja: Informacja dla podróżnych o czasie przybycia do stacji docelowej
	W celu bardziej efektywnego planowania podróży
	Jako podróżny
	Chcę wiedzieć, o której godzinie dotrę do stacji docelowej

Szablon scenariusza: Obliczanie szacowanego czasu przyjazdu
	Zakładając chcę dostać się z <from> do <to>
	I pociąg odjeżdża o <departureTime> na linii <line>
	Gdy zapytam o godzinę przyjazdu pociągu
	Wtedy uzyskam szacowany czas przyjazdu: <arrivalTime>

	Przykłady:
		| line     | from   	| to       | departureTime | arrivalTime
		| Western  | Parramatta | TownHall | 8:02      	   | 8:29
		| Northern | Epping 	| Central  | 8:03          | 8:48
		| Newcastle| Epping 	| Central  | 8:07          | 8:37
		| Newcastle| Epping 	| Central  | 8:07          | 8:37
		| Epping   | Epping 	| Central  | 8:13          | 8:51
