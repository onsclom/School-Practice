grammar Infix;

goal        :   expression EOF;

expression  :   term ((PLUS | MINUS) term)*;
term        :   factor ((MUL | DIV) factor)*;
factor      :   atom (POWER atom)*;
atom        :   integer | LBRACE expression RBRACE;

integer     :   digit (digit)*;
digit       :   NUMBER;

PLUS        :   '+';
MINUS       :   '-';
MUL         :   '*';
DIV         :   '/';
POWER       :   '**';
LBRACE      :   '(';
RBRACE      :   ')';
NUMBER     :   [0-9];
WS          :   [ \t\r\n]+ -> skip;
