grammar StormMusic;

music: (expr NEWLINE)* ;

expr: (title | author | key | tempo | melody);

title: 'title' SPACE (TEXT | SPACE)+;
author: 'author' SPACE (TEXT | SPACE)+;
key: 'key' SPACE keyChoice;
tempo: 'tempo' SPACE INT;
melody: 'melody' SPACE (NOTE | SPACE)+;
keyChoice: ('FA' | 'SOL');

NEWLINE: [\r\n]+ ;
SPACE: ' '+;
NOTE: '-'?[1-4][a-gA-G][0-9];
TEXT: [a-zA-Z_-]+;
INT: [0-9]+;
