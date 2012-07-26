package Pdat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;

class Token {
	public int kind;    // token kind
	public int pos;     // token position in the source text (starting at 0)
	public int col;     // token column (starting at 0)
	public int line;    // token line (starting at 1)
	public String val;  // token value
	public Token next;  // AW 2003-03-07 Tokens are kept in linked list
}

class Buffer {
	public static final char EOF = (char)256;
	static byte[] buf;
	static int bufLen;
	static int pos;

	public static void Fill (FileInputStream s) {
		try {
			bufLen = s.available();
			buf = new byte[bufLen];
			s.read(buf, 0, bufLen);
			pos = 0;
		} catch (IOException e){
			System.out.println("--- error on filling the buffer ");
			System.exit(1);
		}
	}

	public static int Read () {
		if (pos < bufLen) return buf[pos++] & 0xff;  // mask out sign bits
		else return EOF;                             /* pdt */
	}

	public static int Peek () {
		if (pos < bufLen) return buf[pos] & 0xff;    // mask out sign bits
		else return EOF;                             /* pdt */
	}

	/* AW 2003-03-10 moved this from ParserGen.cs */
	public static String GetString (int beg, int end) {
		StringBuffer s = new StringBuffer(64);
		int oldPos = Buffer.getPos();
		Buffer.setPos(beg);
		while (beg < end) { s.append((char)Buffer.Read()); beg++; }
		Buffer.setPos(oldPos);
		return s.toString();
	}

	public static int getPos() {
		return pos;
	}

	public static void setPos (int value) {
		if (value < 0) pos = 0;
		else if (value >= bufLen) pos = bufLen;
		else pos = value;
	}

} // end Buffer

public class Scanner {
	static final char EOL = '\n';
	static final int  eofSym = 0;
	static final int charSetSize = 256;
	static final int maxT = 49;
	static final int noSym = 49;
	// terminals
	static final int EOF_SYM = 0;
	static final int data_Sym = 1;
	static final int str_Sym = 2;
	static final int Project_Sym = 3;
	static final int lbrack_Sym = 4;
	static final int entityTypesequal_Sym = 5;
	static final int null_Sym = 6;
	static final int comma_Sym = 7;
	static final int rbrack_Sym = 8;
	static final int headequal_Sym = 9;
	static final int EntityType_Sym = 10;
	static final int nameequal_Sym = 11;
	static final int fieldsequal_Sym = 12;
	static final int visibleFieldsequal_Sym = 13;
	static final int fieldDefaultsequal_Sym = 14;
	static final int formattingequal_Sym = 15;
	static final int borderCasingequal_Sym = 16;
	static final int isTextFieldequal_Sym = 17;
	static final int dateequal_Sym = 18;
	static final int isVisibleequal_Sym = 19;
	static final int defaultWeightequal_Sym = 20;
	static final int indexequal_Sym = 21;
	static final int MarkEntity_Sym = 22;
	static final int markequal_Sym = 23;
	static final int detailsequal_Sym = 24;
	static final int subEntityWeightequal_Sym = 25;
	static final int subEntityequal_Sym = 26;
	static final int EntityDetails_Sym = 27;
	static final int typeequal_Sym = 28;
	static final int Pictureequal_Sym = 29;
	static final int absentExcuseequal_Sym = 30;
	static final int BorderCase_Sym = 31;
	static final int lowValequal_Sym = 32;
	static final int highValequal_Sym = 33;
	static final int Format_Sym = 34;
	static final int conditionequal_Sym = 35;
	static final int priorityequal_Sym = 36;
	static final int value1equal_Sym = 37;
	static final int value2equal_Sym = 38;
	static final int textColorequal_Sym = 39;
	static final int highlightColorequal_Sym = 40;
	static final int descriptionequal_Sym = 41;
	static final int javapointawtpointColorlbrack_Sym = 42;
	static final int requal_Sym = 43;
	static final int gequal_Sym = 44;
	static final int bequal_Sym = 45;
	static final int point_Sym = 46;
	static final int true_Sym = 47;
	static final int false_Sym = 48;
	static final int NOT_SYM = 49;
	// pragmas

	static short[] start = {
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  1,  2,  1,  1,  1,  1,  1,  1,  1,  1,  1,  6,  1, 50,  1,
	  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  0,  1,  1,
	  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,
	 62,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  4,  1,  7,  1,  1,
	  1, 63, 56, 65, 58, 51, 54, 69, 52, 57, 67,  1, 64, 59, 53,  1,
	 66,  1, 68, 60, 61,  1, 55,  1,  1,  1,  1,  1,  0,  1,  1,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  -1};


	static Token t;          // current token
	static char ch;          // current input character
	static int pos;          // column number of current character
	static int line;         // line number of current character
	static int lineStart;    // start position of current line
	static int oldEols;      // EOLs that appeared in a comment;
	static BitSet ignore;    // set of characters to be ignored by the scanner

	static Token tokens;     // the complete input token stream
	static Token pt;         // current peek token

	public static void Init (String fileName) {
		FileInputStream s = null;
		try {
			s = new FileInputStream(fileName);
			Init(s);
		} catch (IOException e) {
			System.out.println("--- Cannot open file " + fileName);
			System.exit(1);
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("--- Cannot close file " + fileName);
					System.exit(1);
				}
			}
		}
	}

	public static void Init (FileInputStream s) {
		Buffer.Fill(s);
		pos = -1; line = 1; lineStart = 0;
		oldEols = 0;
		NextCh();
		ignore = new BitSet(charSetSize+1);
		ignore.set(' '); // blanks are always white space
		ignore.set(0); ignore.set(1); ignore.set(2); ignore.set(3); 
		ignore.set(4); ignore.set(5); ignore.set(6); ignore.set(7); 
		ignore.set(8); ignore.set(9); ignore.set(10); ignore.set(11); 
		ignore.set(12); ignore.set(13); ignore.set(14); ignore.set(15); 
		ignore.set(16); ignore.set(17); ignore.set(18); ignore.set(19); 
		ignore.set(20); ignore.set(21); ignore.set(22); ignore.set(23); 
		ignore.set(24); ignore.set(25); ignore.set(26); ignore.set(27); 
		ignore.set(28); ignore.set(29); ignore.set(30); ignore.set(31); 
		
		//--- AW: fill token list
		tokens = new Token();  // first token is a dummy
		Token node = tokens;
		do {
			node.next = NextToken();
			node = node.next;
		} while (node.kind != eofSym);
		node.next = node;
		node.val = "EOF";
		t = pt = tokens;
	}

	static void NextCh() {
		if (oldEols > 0) { ch = EOL; oldEols--; }
		else {
			ch = (char)Buffer.Read(); pos++;
			// replace isolated '\r' by '\n' in order to make
			// eol handling uniform across Windows, Unix and Mac
			if (ch == '\r' && Buffer.Peek() != '\n') ch = EOL;
			if (ch == EOL) { line++; lineStart = pos + 1; }
		}

	}



	static void CheckLiteral() {
		String lit = t.val;
		if (lit.compareTo("Project") == 0) t.kind = Project_Sym;
		else if (lit.compareTo("null") == 0) t.kind = null_Sym;
		else if (lit.compareTo("EntityType") == 0) t.kind = EntityType_Sym;
		else if (lit.compareTo("MarkEntity") == 0) t.kind = MarkEntity_Sym;
		else if (lit.compareTo("EntityDetails") == 0) t.kind = EntityDetails_Sym;
		else if (lit.compareTo("BorderCase") == 0) t.kind = BorderCase_Sym;
		else if (lit.compareTo("Format") == 0) t.kind = Format_Sym;
		else if (lit.compareTo("true") == 0) t.kind = true_Sym;
		else if (lit.compareTo("false") == 0) t.kind = false_Sym;
	}

	/* AW Scan() renamed to NextToken() */
	static Token NextToken() {
		while (ignore.get(ch)) NextCh();

		t = new Token();
		t.pos = pos; t.col = pos - lineStart + 1; t.line = line;
		int state = start[ch];
		StringBuffer buf = new StringBuffer(16);
		buf.append(ch); NextCh();
		boolean done = false;
		while (!done) {
			switch (state) {
				case -1: { t.kind = eofSym; done = true; break; }  // NextCh already done /* pdt */
				case 0: { t.kind = noSym; done = true; break; }    // NextCh already done
				case 1:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 2:
					if ((ch == '"')) { buf.append(ch); NextCh(); state = 3; break;}
					else if ((ch >= ' ' && ch <= '!'
					  || ch >= '#' && ch <= '+'
					  || ch >= '-' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 2; break;}
					else { t.kind = noSym; done = true; break; }
				case 3:
					{ t.kind = str_Sym; done = true; break; }
				case 4:
					{ t.kind = lbrack_Sym; done = true; break; }
				case 5:
					{ t.kind = entityTypesequal_Sym; done = true; break; }
				case 6:
					{ t.kind = comma_Sym; done = true; break; }
				case 7:
					{ t.kind = rbrack_Sym; done = true; break; }
				case 8:
					{ t.kind = headequal_Sym; done = true; break; }
				case 9:
					{ t.kind = nameequal_Sym; done = true; break; }
				case 10:
					{ t.kind = fieldsequal_Sym; done = true; break; }
				case 11:
					{ t.kind = visibleFieldsequal_Sym; done = true; break; }
				case 12:
					{ t.kind = fieldDefaultsequal_Sym; done = true; break; }
				case 13:
					{ t.kind = formattingequal_Sym; done = true; break; }
				case 14:
					{ t.kind = borderCasingequal_Sym; done = true; break; }
				case 15:
					{ t.kind = isTextFieldequal_Sym; done = true; break; }
				case 16:
					{ t.kind = dateequal_Sym; done = true; break; }
				case 17:
					{ t.kind = isVisibleequal_Sym; done = true; break; }
				case 18:
					{ t.kind = defaultWeightequal_Sym; done = true; break; }
				case 19:
					{ t.kind = indexequal_Sym; done = true; break; }
				case 20:
					{ t.kind = markequal_Sym; done = true; break; }
				case 21:
					{ t.kind = detailsequal_Sym; done = true; break; }
				case 22:
					{ t.kind = subEntityWeightequal_Sym; done = true; break; }
				case 23:
					{ t.kind = subEntityequal_Sym; done = true; break; }
				case 24:
					{ t.kind = typeequal_Sym; done = true; break; }
				case 25:
					{ t.kind = Pictureequal_Sym; done = true; break; }
				case 26:
					{ t.kind = absentExcuseequal_Sym; done = true; break; }
				case 27:
					{ t.kind = lowValequal_Sym; done = true; break; }
				case 28:
					{ t.kind = highValequal_Sym; done = true; break; }
				case 29:
					{ t.kind = conditionequal_Sym; done = true; break; }
				case 30:
					{ t.kind = priorityequal_Sym; done = true; break; }
				case 31:
					{ t.kind = value1equal_Sym; done = true; break; }
				case 32:
					{ t.kind = value2equal_Sym; done = true; break; }
				case 33:
					{ t.kind = textColorequal_Sym; done = true; break; }
				case 34:
					{ t.kind = highlightColorequal_Sym; done = true; break; }
				case 35:
					{ t.kind = descriptionequal_Sym; done = true; break; }
				case 36:
					if (ch == 'a') { buf.append(ch); NextCh(); state = 37; break;}
					else { t.kind = noSym; done = true; break; }
				case 37:
					if (ch == 'w') { buf.append(ch); NextCh(); state = 38; break;}
					else { t.kind = noSym; done = true; break; }
				case 38:
					if (ch == 't') { buf.append(ch); NextCh(); state = 39; break;}
					else { t.kind = noSym; done = true; break; }
				case 39:
					if (ch == '.') { buf.append(ch); NextCh(); state = 40; break;}
					else { t.kind = noSym; done = true; break; }
				case 40:
					if (ch == 'C') { buf.append(ch); NextCh(); state = 41; break;}
					else { t.kind = noSym; done = true; break; }
				case 41:
					if (ch == 'o') { buf.append(ch); NextCh(); state = 42; break;}
					else { t.kind = noSym; done = true; break; }
				case 42:
					if (ch == 'l') { buf.append(ch); NextCh(); state = 43; break;}
					else { t.kind = noSym; done = true; break; }
				case 43:
					if (ch == 'o') { buf.append(ch); NextCh(); state = 44; break;}
					else { t.kind = noSym; done = true; break; }
				case 44:
					if (ch == 'r') { buf.append(ch); NextCh(); state = 45; break;}
					else { t.kind = noSym; done = true; break; }
				case 45:
					if (ch == '[') { buf.append(ch); NextCh(); state = 46; break;}
					else { t.kind = noSym; done = true; break; }
				case 46:
					{ t.kind = javapointawtpointColorlbrack_Sym; done = true; break; }
				case 47:
					{ t.kind = requal_Sym; done = true; break; }
				case 48:
					{ t.kind = gequal_Sym; done = true; break; }
				case 49:
					{ t.kind = bequal_Sym; done = true; break; }
				case 50:
					{ t.kind = point_Sym; done = true; break; }
				case 51:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 70; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 52:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 71; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 72; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 53:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 73; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 54:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 74; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 75; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 55:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 76; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 77; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 56:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 78; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 49; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 57:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 79; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 80; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 58:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 81; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 82; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 59:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 83; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 60:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 't'
					  || ch >= 'v' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'u') { buf.append(ch); NextCh(); state = 84; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 61:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= 'x'
					  || ch >= 'z' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'y') { buf.append(ch); NextCh(); state = 85; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 86; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 62:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 87; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 63:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'a'
					  || ch >= 'c' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'b') { buf.append(ch); NextCh(); state = 88; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 64:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 89; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 65:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 90; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 66:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 91; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 67:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 92; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 68:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 47; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 69:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 48; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 70:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 93; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 71:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 94; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 72:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'f'
					  || ch >= 'h' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'g') { buf.append(ch); NextCh(); state = 95; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 73:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'l'
					  || ch >= 'n' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'm') { buf.append(ch); NextCh(); state = 96; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 74:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 97; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 75:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 98; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 76:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 99; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 77:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 100; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 78:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 101; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 79:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'S'
					  || ch == 'U'
					  || ch >= 'W' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'T') { buf.append(ch); NextCh(); state = 102; break;}
					else if (ch == 'V') { buf.append(ch); NextCh(); state = 103; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 80:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'c'
					  || ch >= 'e' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'd') { buf.append(ch); NextCh(); state = 104; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 81:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 105; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 82:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'e'
					  || ch >= 'g' && ch <= 'r'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'f') { buf.append(ch); NextCh(); state = 106; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 107; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 108; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 83:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 109; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 84:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'a'
					  || ch >= 'c' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'b') { buf.append(ch); NextCh(); state = 110; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 85:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'o'
					  || ch >= 'q' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'p') { buf.append(ch); NextCh(); state = 111; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 86:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'w'
					  || ch >= 'y' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'x') { buf.append(ch); NextCh(); state = 112; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 87:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'b'
					  || ch >= 'd' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'c') { buf.append(ch); NextCh(); state = 113; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 88:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 114; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 89:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'v'
					  || ch >= 'x' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'w') { buf.append(ch); NextCh(); state = 115; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 90:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 116; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 91:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 117; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 92:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'u'
					  || ch >= 'w' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'v') { buf.append(ch); NextCh(); state = 118; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 93:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 119; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 94:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'c'
					  || ch >= 'e' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'd') { buf.append(ch); NextCh(); state = 120; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 95:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'g'
					  || ch >= 'i' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'h') { buf.append(ch); NextCh(); state = 121; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 96:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 122; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 97:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 123; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 98:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'l'
					  || ch >= 'n' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'm') { buf.append(ch); NextCh(); state = 124; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 99:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 125; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 100:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 't'
					  || ch >= 'v' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'u') { buf.append(ch); NextCh(); state = 126; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 101:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'c'
					  || ch >= 'e' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'd') { buf.append(ch); NextCh(); state = 127; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 102:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 128; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 103:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 129; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 104:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 130; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 105:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 131; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 106:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 132; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 107:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 133; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 108:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'b'
					  || ch >= 'd' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'c') { buf.append(ch); NextCh(); state = 134; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 109:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'j'
					  || ch >= 'l' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'k') { buf.append(ch); NextCh(); state = 135; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 110:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'D'
					  || ch >= 'F' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'E') { buf.append(ch); NextCh(); state = 136; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 111:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 137; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 112:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 138; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 113:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 139; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 114:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 140; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 115:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'U'
					  || ch >= 'W' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'V') { buf.append(ch); NextCh(); state = 141; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 116:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'c'
					  || ch >= 'e' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'd') { buf.append(ch); NextCh(); state = 142; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 117:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 143; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 118:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 144; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 119:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 145; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 120:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 8; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 121:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'U'
					  || ch >= 'W' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'V') { buf.append(ch); NextCh(); state = 146; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 147; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 122:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 9; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 123:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'c'
					  || ch >= 'e' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'd') { buf.append(ch); NextCh(); state = 148; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 124:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 149; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 125:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'a'
					  || ch >= 'c' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'b') { buf.append(ch); NextCh(); state = 150; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 126:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 151; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 127:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 152; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 128:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'w'
					  || ch >= 'y' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'x') { buf.append(ch); NextCh(); state = 153; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 129:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 154; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 130:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'w'
					  || ch >= 'y' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'x') { buf.append(ch); NextCh(); state = 155; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 131:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 16; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 132:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 't'
					  || ch >= 'v' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'u') { buf.append(ch); NextCh(); state = 156; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 133:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 157; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 134:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 158; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 135:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 20; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 136:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 159; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 137:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 24; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 138:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'B'
					  || ch >= 'D' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'C') { buf.append(ch); NextCh(); state = 160; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 139:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 't'
					  || ch >= 'v' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'u') { buf.append(ch); NextCh(); state = 161; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 140:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 162; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 141:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 163; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 142:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 164; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 143:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 165; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 144:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '.') { buf.append(ch); NextCh(); state = 36; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 145:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'x'
					  || ch >= 'z' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'y') { buf.append(ch); NextCh(); state = 166; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 146:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 167; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 147:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 168; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 148:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'C'
					  || ch >= 'E' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 169; break;}
					else if (ch == 'D') { buf.append(ch); NextCh(); state = 170; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 149:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 171; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 150:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 172; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 151:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '0'
					  || ch >= '3' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '1') { buf.append(ch); NextCh(); state = 173; break;}
					else if (ch == '2') { buf.append(ch); NextCh(); state = 174; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 152:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 175; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 153:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 176; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 154:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 177; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 155:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 19; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 156:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 178; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 157:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 179; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 158:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 180; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 159:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 181; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 160:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 182; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 161:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 183; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 162:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 184; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 163:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 185; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 164:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 186; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 165:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 187; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 166:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'S'
					  || ch >= 'U' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'T') { buf.append(ch); NextCh(); state = 188; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 167:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 189; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 168:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'f'
					  || ch >= 'h' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'g') { buf.append(ch); NextCh(); state = 190; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 169:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 10; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 170:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 191; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 171:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 192; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 172:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 193; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 173:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 31; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 174:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 32; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 175:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'B'
					  || ch >= 'D' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'C') { buf.append(ch); NextCh(); state = 194; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 176:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'E'
					  || ch >= 'G' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'F') { buf.append(ch); NextCh(); state = 195; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 177:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'a'
					  || ch >= 'c' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'b') { buf.append(ch); NextCh(); state = 196; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 178:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 197; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 179:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 198; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 180:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'o'
					  || ch >= 'q' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'p') { buf.append(ch); NextCh(); state = 199; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 181:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 200; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 182:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 201; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 183:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 202; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 184:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'D'
					  || ch >= 'F' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'E') { buf.append(ch); NextCh(); state = 203; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 185:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 27; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 186:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 204; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 187:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 205; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 188:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'x'
					  || ch >= 'z' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'y') { buf.append(ch); NextCh(); state = 206; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 189:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 28; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 190:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'g'
					  || ch >= 'i' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'h') { buf.append(ch); NextCh(); state = 207; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 191:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'e'
					  || ch >= 'g' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'f') { buf.append(ch); NextCh(); state = 208; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 192:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 209; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 193:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'E'
					  || ch >= 'G' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'F') { buf.append(ch); NextCh(); state = 210; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 194:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 211; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 195:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 212; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 196:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 213; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 197:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'V'
					  || ch >= 'X' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'W') { buf.append(ch); NextCh(); state = 214; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 198:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 21; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 199:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 215; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 200:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 216; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 201:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 217; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 202:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 25; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 203:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'w'
					  || ch >= 'y' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'x') { buf.append(ch); NextCh(); state = 218; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 204:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 219; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 205:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'x'
					  || ch >= 'z' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'y') { buf.append(ch); NextCh(); state = 220; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 206:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'o'
					  || ch >= 'q' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'p') { buf.append(ch); NextCh(); state = 221; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 207:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 222; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 208:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '`'
					  || ch >= 'b' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'a') { buf.append(ch); NextCh(); state = 223; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 209:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 224; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 210:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 225; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 211:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 226; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 212:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 227; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 213:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 228; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 214:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 229; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 215:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 230; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 216:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'x'
					  || ch >= 'z' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'y') { buf.append(ch); NextCh(); state = 231; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 217:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 232; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 218:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'b'
					  || ch >= 'd' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'c') { buf.append(ch); NextCh(); state = 233; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 219:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 234; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 220:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 30; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 221:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 235; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 222:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'B'
					  || ch >= 'D' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'C') { buf.append(ch); NextCh(); state = 236; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 223:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 't'
					  || ch >= 'v' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'u') { buf.append(ch); NextCh(); state = 237; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 224:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'f'
					  || ch >= 'h' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'g') { buf.append(ch); NextCh(); state = 238; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 225:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 239; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 226:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 240; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 227:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 241; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 228:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 17; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 229:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 242; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 230:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 243; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 231:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'V'
					  || ch >= 'X' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'W') { buf.append(ch); NextCh(); state = 244; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 23; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 232:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 33; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 233:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 't'
					  || ch >= 'v' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'u') { buf.append(ch); NextCh(); state = 245; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 234:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 29; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 235:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 246; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 236:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 247; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 237:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 248; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 238:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 13; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 239:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 249; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 240:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 250; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 241:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'c'
					  || ch >= 'e' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'd') { buf.append(ch); NextCh(); state = 251; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 242:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'f'
					  || ch >= 'h' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'g') { buf.append(ch); NextCh(); state = 252; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 243:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'm'
					  || ch >= 'o' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'n') { buf.append(ch); NextCh(); state = 253; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 244:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 254; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 245:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 255; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 246:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 5; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 247:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'k'
					  || ch >= 'm' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'l') { buf.append(ch); NextCh(); state = 256; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 248:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 257; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 249:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'c'
					  || ch >= 'e' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'd') { buf.append(ch); NextCh(); state = 258; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 250:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'f'
					  || ch >= 'h' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'g') { buf.append(ch); NextCh(); state = 259; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 251:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 15; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 252:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'g'
					  || ch >= 'i' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'h') { buf.append(ch); NextCh(); state = 260; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 253:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 35; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 254:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'h'
					  || ch >= 'j' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'i') { buf.append(ch); NextCh(); state = 261; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 255:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'd'
					  || ch >= 'f' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'e') { buf.append(ch); NextCh(); state = 262; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 256:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'n'
					  || ch >= 'p' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'o') { buf.append(ch); NextCh(); state = 263; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 257:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 264; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 258:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'r'
					  || ch >= 't' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 's') { buf.append(ch); NextCh(); state = 265; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 259:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 14; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 260:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 266; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 261:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'f'
					  || ch >= 'h' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'g') { buf.append(ch); NextCh(); state = 267; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 262:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 26; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 263:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'q'
					  || ch >= 's' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'r') { buf.append(ch); NextCh(); state = 268; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 264:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 12; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 265:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 11; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 266:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 18; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 267:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 'g'
					  || ch >= 'i' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 'h') { buf.append(ch); NextCh(); state = 269; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 268:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 34; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 269:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= 's'
					  || ch >= 'u' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == 't') { buf.append(ch); NextCh(); state = 270; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }
				case 270:
					if ((ch == '!'
					  || ch >= '#' && ch <= '+'
					  || ch == '-'
					  || ch >= '/' && ch <= '<'
					  || ch >= '>' && ch <= 'Z'
					  || ch == 92
					  || ch >= '^' && ch <= '{'
					  || ch >= '}' && ch <= '~')) { buf.append(ch); NextCh(); state = 1; break;}
					else if (ch == '=') { buf.append(ch); NextCh(); state = 22; break;}
					else { t.kind = data_Sym; t.val = buf.toString(); CheckLiteral(); return t; }

			}
		}
		t.val = buf.toString();
		return t;
	}

	/* AW 2003-03-07 get the next token, move on and synch peek token with current */
	public static Token Scan () {
		t = pt = t.next;
		return t;
	}

	/* AW 2003-03-07 get the next token, ignore pragmas */
	public static Token Peek () {
		do {                      // skip pragmas while peeking
			pt = pt.next;
		} while (pt.kind > maxT);
		return pt;
	}

	/* AW 2003-03-11 to make sure peek start at current scan position */
	public static void ResetPeek () { pt = t; }

} // end Scanner
