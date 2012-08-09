package Pdat;

import ClassAdminBackEnd.*;
import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;
import java.lang.reflect.Array;

import java.io.*;

public class Parser {
	static final int _EOF = 0;
	static final int _data = 1;
	static final int _str = 2;
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

	static final int maxT = 49;

	static final boolean T = true;
	static final boolean x = false;
	static final int minErrDist = 2;

	public static Token token;    // last recognized token   /* pdt */
	public static Token la;       // lookahead token
	static int errDist = minErrDist;

	

	static void SynErr (int n) {
		if (errDist >= minErrDist) Errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public static void SemErr (String msg) {
		if (errDist >= minErrDist) Errors.Error(token.line, token.col, msg); /* pdt */
		errDist = 0;
	}

	public static void SemError (String msg) {
		if (errDist >= minErrDist) Errors.Error(token.line, token.col, msg); /* pdt */
		errDist = 0;
	}

	public static void Warning (String msg) { /* pdt */
		if (errDist >= minErrDist) Errors.Warn(token.line, token.col, msg);
		errDist = 0;
	}

	public static boolean Successful() { /* pdt */
		return Errors.count == 0;
	}

	public static String LexString() { /* pdt */
		return token.val;
	}

	public static String LookAheadString() { /* pdt */
		return la.val;
	}

	static void Get () {
		for (;;) {
			token = la; /* pdt */
			la = Scanner.Scan();
			if (la.kind <= maxT) { ++errDist; break; }

			la = token; /* pdt */
		}
	}

	static void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}

	static boolean StartOf (int s) {
		return set[s][la.kind];
	}

	static void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}

	static boolean WeakSeparator (int n, int syFol, int repFol) {
		boolean[] s = new boolean[maxT+1];
		if (la.kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			for (int i=0; i <= maxT; i++) {
				s[i] = set[syFol][i] || set[repFol][i] || set[0][i];
			}
			SynErr(n);
			while (!s[la.kind]) Get();
			return StartOf(syFol);
		}
	}

	static void Pdat() {
		Expect(Project_Sym);
		Global.getGlobal().getProjects().set(Global.getGlobal().getActiveProjectIndex(), new Project());
		Expect(lbrack_Sym);
		Expect(entityTypesequal_Sym);
		Global.getGlobal().getActiveProject().getEntityTypes().clear();
		if (la.kind == null_Sym) {
			Get();
		} else if (la.kind == lbrack_Sym) {
			Get();
			EntityType();
			while (la.kind == comma_Sym) {
				Get();
				EntityType();
			}
			Expect(rbrack_Sym);
		} else SynErr(50);
		Expect(comma_Sym);
		Expect(headequal_Sym);
		SuperEntity head = SuperEntity();
		Global.getGlobal().getActiveProject().setHead(head);
		Expect(rbrack_Sym);
	}

	static void EntityType() {
		Expect(EntityType_Sym);
		Expect(lbrack_Sym);
		EntityType ent = new EntityType("");
		Global.getGlobal().getActiveProject().getEntityTypes().add(ent);
		Expect(nameequal_Sym);
		String name = pString();
		ent.setName(name);
		Expect(comma_Sym);
		Expect(fieldsequal_Sym);
		Expect(lbrack_Sym);
		if (la.kind == rbrack_Sym) {
			Get();
		} else if (la.kind == str_Sym) {
			String field = pString();
			ent.getFields().add(field);
			while (la.kind == comma_Sym) {
				Get();
				field = pString();
				ent.getFields().add(field);
			}
			Expect(rbrack_Sym);
		} else SynErr(51);
		Expect(comma_Sym);
		Expect(visibleFieldsequal_Sym);
		if (la.kind == lbrack_Sym) {
			Get();
			if (la.kind == rbrack_Sym) {
				Get();
			} else if (la.kind == true_Sym || la.kind == false_Sym) {
				LinkedList<Boolean> tempBoolList = new LinkedList<Boolean>();
				Boolean bool = Boolean();
				tempBoolList.add(bool);
				while (la.kind == comma_Sym) {
					Get();
					bool = Boolean();
					tempBoolList.add(bool);
				}
				Expect(rbrack_Sym);
				ent.setVisibleFields(pdatImport.toBoolArray(tempBoolList));
			} else SynErr(52);
		} else if (la.kind == null_Sym) {
			Get();
		} else SynErr(53);
		Expect(comma_Sym);
		Expect(fieldDefaultsequal_Sym);
		Expect(lbrack_Sym);
		if (la.kind == rbrack_Sym) {
			Get();
		} else if (la.kind == str_Sym) {
			String fieldDef = pString();
			ent.getFieldDefaults().add(fieldDef);
			while (la.kind == comma_Sym) {
				Get();
				fieldDef = pString();
				ent.getFieldDefaults().add(fieldDef);
			}
			Expect(rbrack_Sym);
		} else SynErr(54);
		Expect(comma_Sym);
		Expect(formattingequal_Sym);
		Expect(lbrack_Sym);
		if (la.kind == rbrack_Sym) {
			Get();
		} else if (la.kind == Format_Sym) {
			Format format = Format();
			ent.getFormatting().add(format);
			while (la.kind == comma_Sym) {
				Get();
				format = Format();
				ent.getFormatting().add(format);
			}
			Expect(rbrack_Sym);
		} else SynErr(55);
		Expect(comma_Sym);
		Expect(borderCasingequal_Sym);
		Expect(lbrack_Sym);
		if (la.kind == rbrack_Sym) {
			Get();
		} else if (la.kind == BorderCase_Sym) {
			BorderCase border = BorderCase();
			ent.getBorderCasing().add(border);
			while (la.kind == comma_Sym) {
				Get();
				border = BorderCase();
				ent.getBorderCasing().add(border);
			}
			Expect(rbrack_Sym);
		} else SynErr(56);
		Expect(comma_Sym);
		Expect(isTextFieldequal_Sym);
		Boolean isText = Boolean();
		ent.setIsTextField(isText);
		Expect(comma_Sym);
		Expect(dateequal_Sym);
		String date = pString();
		ent.setDate(pdatImport.ParseDate(date));
		Expect(comma_Sym);
		Expect(isVisibleequal_Sym);
		Boolean isVis = Boolean();
		ent.setIsVisible(isVis);
		Expect(comma_Sym);
		Expect(defaultWeightequal_Sym);
		double Weight = Double();
		ent.setDefaultWeight(Weight);
		Expect(comma_Sym);
		Expect(indexequal_Sym);
		int i = Integer();
		ent.setIndex(i);
		Expect(rbrack_Sym);
	}

	static SuperEntity SuperEntity() {
		SuperEntity returnValue;
		Expect(MarkEntity_Sym);
		Expect(lbrack_Sym);
		Expect(markequal_Sym);
		Double mark = Double();
		Expect(comma_Sym);
		Expect(detailsequal_Sym);
		EntityDetails enDetails = EntityDetails();
		returnValue = new SuperEntity(enDetails,mark);
		Expect(comma_Sym);
		Expect(subEntityWeightequal_Sym);
		Expect(lbrack_Sym);
		if (la.kind == rbrack_Sym) {
			Get();
		} else if (la.kind == data_Sym) {
			double weight = Double();
			returnValue.getSubEntityWeight().add(weight);
			while (la.kind == comma_Sym) {
				Get();
				weight = Double();
				returnValue.getSubEntityWeight().add(weight);
			}
			Expect(rbrack_Sym);
		} else SynErr(57);
		Expect(comma_Sym);
		Expect(subEntityequal_Sym);
		Expect(lbrack_Sym);
		if (la.kind == rbrack_Sym) {
			Get();
		} else if (la.kind == MarkEntity_Sym) {
			SuperEntity child = SuperEntity();
			returnValue.getSubEntity().add(child);
			child.setParentEntity(returnValue);
			while (la.kind == comma_Sym) {
				Get();
				child = SuperEntity();
				returnValue.getSubEntity().add(child);
				child.setParentEntity(returnValue);
			}
			Expect(rbrack_Sym);
		} else SynErr(58);
		Expect(rbrack_Sym);
		return returnValue;
	}

	static String pString() {
		String returnValue;
		Expect(str_Sym);
		returnValue = token.val.substring(1,token.val.length()-1);
		return returnValue;
	}

	static Boolean Boolean() {
		Boolean returnValue;
		returnValue = false;
		if (la.kind == true_Sym) {
			Get();
			returnValue = true;
		} else if (la.kind == false_Sym) {
			Get();
			returnValue = false;
		} else SynErr(59);
		return returnValue;
	}

	static Format Format() {
		Format returnValue;
		Expect(Format_Sym);
		Expect(lbrack_Sym);
		Expect(conditionequal_Sym);
		int cond = Integer();
		Expect(comma_Sym);
		Expect(priorityequal_Sym);
		int prior = Integer();
		Expect(comma_Sym);
		Expect(value1equal_Sym);
		Double val1 = Double();
		Expect(comma_Sym);
		Expect(value2equal_Sym);
		Double val2 = Double();
		Expect(comma_Sym);
		Expect(textColorequal_Sym);
		Color tCol = Color();
		Expect(comma_Sym);
		Expect(highlightColorequal_Sym);
		Color hCol = Color();
		Expect(comma_Sym);
		Expect(descriptionequal_Sym);
		String desc = pString();
		returnValue = new Format(cond,prior,val1,val2,tCol,hCol,desc);
		Expect(rbrack_Sym);
		return returnValue;
	}

	static BorderCase BorderCase() {
		BorderCase returnValue;
		Expect(BorderCase_Sym);
		Expect(lbrack_Sym);
		Expect(lowValequal_Sym);
		Double lVal = Double();
		Expect(comma_Sym);
		Expect(highValequal_Sym);
		Double hVal = Double();
		returnValue = new BorderCase(lVal,hVal);
		Expect(rbrack_Sym);
		return returnValue;
	}

	static Double Double() {
		Double returnValue;
		String doub ="";
		Expect(data_Sym);
		doub += token.val;
		Expect(point_Sym);
		Expect(data_Sym);
		doub += "."+token.val;
		returnValue = Double.parseDouble(doub);
		return returnValue;
	}

	static int Integer() {
		int returnValue;
		Expect(data_Sym);
		returnValue = Integer.parseInt(token.val);
		return returnValue;
	}

	static EntityDetails EntityDetails() {
		EntityDetails returnValue;
		Expect(EntityDetails_Sym);
		Expect(lbrack_Sym);
		Expect(typeequal_Sym);
		int type = Integer();
		returnValue = new EntityDetails(type);
		Expect(comma_Sym);
		Expect(fieldsequal_Sym);
		Expect(lbrack_Sym);
		if (la.kind == rbrack_Sym) {
			Get();
		} else if (la.kind == str_Sym) {
			String field = pString();
			returnValue.getFields().add(field);
			while (la.kind == comma_Sym) {
				Get();
				field = pString();
				returnValue.getFields().add(field);
			}
			Expect(rbrack_Sym);
		} else SynErr(60);
		Expect(comma_Sym);
		Expect(Pictureequal_Sym);
		String pic = pString();
		returnValue.setPicture(pic);
		Expect(comma_Sym);
		Expect(absentExcuseequal_Sym);
		Boolean absentEx = Boolean();
		returnValue.setAbsentExcuse(absentEx);
		Expect(rbrack_Sym);
		return returnValue;
	}

	static Color Color() {
		Color returnValue;
		Expect(javapointawtpointColorlbrack_Sym);
		Expect(requal_Sym);
		int r = Integer();
		Expect(comma_Sym);
		Expect(gequal_Sym);
		int g = Integer();
		Expect(comma_Sym);
		Expect(bequal_Sym);
		int b = Integer();
		returnValue = new Color(r,g,b);
		Expect(rbrack_Sym);
		return returnValue;
	}



	public static void Parse() {
		la = new Token();
		la.val = "";
		Get();
		Pdat();
		Expect(EOF_SYM);

	}

	private static boolean[][] set = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x}

	};

} // end Parser

/* pdt - considerable extension from here on */

class ErrorRec {
	public int line, col, num;
	public String str;
	public ErrorRec next;

	public ErrorRec(int l, int c, String s) {
		line = l; col = c; str = s; next = null;
	}

} // end ErrorRec

class Errors {

	public static int count = 0;                                     // number of errors detected
	public static String errMsgFormat = "file {0} : ({1}, {2}) {3}"; // 0=file 1=line, 2=column, 3=text
	static String fileName = "";
	static String listName = "";
	static boolean mergeErrors = false;
	static PrintWriter mergedList;

	static ErrorRec first = null, last;
	static boolean eof = false;

	static String getLine() {
		char ch, CR = '\r', LF = '\n';
		int l = 0;
		StringBuffer s = new StringBuffer();
		ch = (char) Buffer.Read();
		while (ch != Buffer.EOF && ch != CR && ch != LF) {
			s.append(ch); l++; ch = (char) Buffer.Read();
		}
		eof = (l == 0 && ch == Buffer.EOF);
		if (ch == CR) {  // check for MS-DOS
			ch = (char) Buffer.Read();
			if (ch != LF && ch != Buffer.EOF) Buffer.pos--;
		}
		return s.toString();
	}

	static private String Int(int n, int len) {
		String s = String.valueOf(n);
		int i = s.length(); if (len < i) len = i;
		int j = 0, d = len - s.length();
		char[] a = new char[len];
		for (i = 0; i < d; i++) a[i] = ' ';
		for (j = 0; i < len; i++) {a[i] = s.charAt(j); j++;}
		return new String(a, 0, len);
	}

	static void display(String s, ErrorRec e) {
		mergedList.print("**** ");
		for (int c = 1; c < e.col; c++)
			if (s.charAt(c-1) == '\t') mergedList.print("\t"); else mergedList.print(" ");
		mergedList.println("^ " + e.str);
	}

	public static void Init (String fn, String dir, boolean merge) {
		fileName = fn;
		listName = dir + "listing.txt";
		mergeErrors = merge;
		if (mergeErrors)
			try {
				mergedList = new PrintWriter(new BufferedWriter(new FileWriter(listName, false)));
			} catch (IOException e) {
				Errors.Exception("-- could not open " + listName);
			}
	}

	public static void Summarize () {
		if (mergeErrors) {
			ErrorRec cur = first;
			Buffer.setPos(0);
			int lnr = 1;
			String s = getLine();
			while (!eof) {
				mergedList.println(Int(lnr, 4) + " " + s);
				while (cur != null && cur.line == lnr) {
					display(s, cur); cur = cur.next;
				}
				lnr++; s = getLine();
			}
			if (cur != null) {
				mergedList.println(Int(lnr, 4));
				while (cur != null) {
					display(s, cur); cur = cur.next;
				}
			}
			mergedList.println();
			mergedList.println(count + " errors detected");
			mergedList.close();
		}
		switch (count) {
			case 0 : System.out.println("Parsed correctly"); break;
			case 1 : System.out.println("1 error detected"); break;
			default: System.out.println(count + " errors detected"); break;
		}
		if (count > 0 && mergeErrors) System.out.println("see " + listName);
	}

	public static void storeError (int line, int col, String s) {
		if (mergeErrors) {
			ErrorRec latest = new ErrorRec(line, col, s);
			if (first == null) first = latest; else last.next = latest;
			last = latest;
		} else printMsg(fileName, line, col, s);
	}

	public static void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "data expected"; break;
			case 2: s = "str expected"; break;
			case 3: s = "\"Project\" expected"; break;
			case 4: s = "\"[\" expected"; break;
			case 5: s = "\"entityTypes=\" expected"; break;
			case 6: s = "\"null\" expected"; break;
			case 7: s = "\",\" expected"; break;
			case 8: s = "\"]\" expected"; break;
			case 9: s = "\"head=\" expected"; break;
			case 10: s = "\"EntityType\" expected"; break;
			case 11: s = "\"name=\" expected"; break;
			case 12: s = "\"fields=\" expected"; break;
			case 13: s = "\"visibleFields=\" expected"; break;
			case 14: s = "\"fieldDefaults=\" expected"; break;
			case 15: s = "\"formatting=\" expected"; break;
			case 16: s = "\"borderCasing=\" expected"; break;
			case 17: s = "\"isTextField=\" expected"; break;
			case 18: s = "\"date=\" expected"; break;
			case 19: s = "\"isVisible=\" expected"; break;
			case 20: s = "\"defaultWeight=\" expected"; break;
			case 21: s = "\"index=\" expected"; break;
			case 22: s = "\"MarkEntity\" expected"; break;
			case 23: s = "\"mark=\" expected"; break;
			case 24: s = "\"details=\" expected"; break;
			case 25: s = "\"subEntityWeight=\" expected"; break;
			case 26: s = "\"subEntity=\" expected"; break;
			case 27: s = "\"EntityDetails\" expected"; break;
			case 28: s = "\"type=\" expected"; break;
			case 29: s = "\"Picture=\" expected"; break;
			case 30: s = "\"absentExcuse=\" expected"; break;
			case 31: s = "\"BorderCase\" expected"; break;
			case 32: s = "\"lowVal=\" expected"; break;
			case 33: s = "\"highVal=\" expected"; break;
			case 34: s = "\"Format\" expected"; break;
			case 35: s = "\"condition=\" expected"; break;
			case 36: s = "\"priority=\" expected"; break;
			case 37: s = "\"value1=\" expected"; break;
			case 38: s = "\"value2=\" expected"; break;
			case 39: s = "\"textColor=\" expected"; break;
			case 40: s = "\"highlightColor=\" expected"; break;
			case 41: s = "\"description=\" expected"; break;
			case 42: s = "\"java.awt.Color[\" expected"; break;
			case 43: s = "\"r=\" expected"; break;
			case 44: s = "\"g=\" expected"; break;
			case 45: s = "\"b=\" expected"; break;
			case 46: s = "\".\" expected"; break;
			case 47: s = "\"true\" expected"; break;
			case 48: s = "\"false\" expected"; break;
			case 49: s = "??? expected"; break;
			case 50: s = "invalid Pdat"; break;
			case 51: s = "invalid EntityType"; break;
			case 52: s = "invalid EntityType"; break;
			case 53: s = "invalid EntityType"; break;
			case 54: s = "invalid EntityType"; break;
			case 55: s = "invalid EntityType"; break;
			case 56: s = "invalid EntityType"; break;
			case 57: s = "invalid MarkEntity"; break;
			case 58: s = "invalid MarkEntity"; break;
			case 59: s = "invalid Boolean"; break;
			case 60: s = "invalid EntityDetails"; break;
			default: s = "error " + n; break;
		}
		storeError(line, col, s);
		count++;
	}

	public static void SemErr (int line, int col, int n) {
		storeError(line, col, ("error " + n));
		count++;
	}

	public static void Error (int line, int col, String s) {
		storeError(line, col, s);
		count++;
	}

	public static void Warn (int line, int col, String s) {
		storeError(line, col, s);
	}

	public static void Exception (String s) {
		System.out.println(s);
		System.exit(1);
	}

	private static void printMsg(String fileName, int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.replace(pos, pos+3, fileName); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{2}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{3}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		System.out.println(b.toString());
	}

} // end Errors
