grammar StormMusic;

music: title NEWLINE+ author NEWLINE+ /*key NEWLINE+ armor NEWLINE+ melody*/;

title: 'title' SPACE+ TEXT;
author: 'author' SPACE+ TEXT;
//key: 'key' SPACE+ ( 'toto' | 'titi' | 'tata' );
//armor: 'armor' SPACE+ (NOTE SPACE*)*;
melody: 'melody' NEWLINE NOTE+;


NEWLINE: ('\r'? '\n' | '\r') ;
SPACE: ' ';
NOTE: [a-g];
TEXT: [a-zA-Z_-]+;
