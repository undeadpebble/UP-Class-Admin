package ClassAdminBackEnd;

import java.awt.Color;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.internal.table.SqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PDatImport {
	private Project project;
	private LinkedList<SuperEntity> entityList = new LinkedList<SuperEntity>();
	private LinkedList<Long> entityList_ID = new LinkedList<Long>();

	private LinkedList<EntityType> entityTypeList = new LinkedList<EntityType>();
	private LinkedList<Long> entityTypeList_ID = new LinkedList<Long>();

	private LinkedList<FormatStruct> formatStructList = new LinkedList<FormatStruct>();
	private LinkedList<Long> formatList_ID = new LinkedList<Long>();
	private LinkedList<Long> formatList_TypeID = new LinkedList<Long>();

	public void importFile(String filename, Project project)
			throws SqlJetException {
		this.project = project;
		File dbFile = new File(filename);

		SqlJetDb db = SqlJetDb.open(dbFile, true);
	}

	private void readTypes(SqlJetDb db) throws SqlJetException {
		ISqlJetTable table = db.getTable(PDatExport.ENTITY_TYPE_TABLE);
		ISqlJetCursor cursor = table.order("typeID");

		try {
			if (!cursor.eof()) {
				do {
					long typeID = cursor.getInteger("typeID");
					String name = cursor.getString("name");
					long parentID = cursor.getInteger("parentID");
					boolean isText = cursor.getBoolean("isText");
					double weight = cursor.getFloat("defaultWeight");
					Date date = new Date(cursor.getInteger("date"));

					entityTypeList_ID.add(typeID);
					if (parentID == 0) {
						EntityType eType = new EntityType(name, null, isText,
								date, weight);
						entityTypeList.add(eType);
						project.setHeadEntityType(eType);
					} else {
						EntityType eType = new EntityType(name,
								entityTypeList.get(entityTypeList_ID
										.indexOf(parentID)), isText, date,
								weight);
						entityTypeList.add(eType);

					}

				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}

	}

	private void readBordercases(SqlJetDb db) throws SqlJetException {
		ISqlJetTable table = db.getTable(PDatExport.BORDERCASE_TABLE);
		ISqlJetCursor cursor = table.order("typeID");
		try {
			if (!cursor.eof()) {
				do {
					long typeID = cursor.getInteger("typeID");
					double low = cursor.getFloat("lowval");
					double high = cursor.getFloat("highval");

					EntityType eType = entityTypeList.get(entityTypeList_ID
							.indexOf(typeID));

					BorderCase bCase = new BorderCase(low, high);
					eType.getBorderCasing().add(bCase);

				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	private void readFormats(SqlJetDb db) throws SqlJetException {
		//format headers
		ISqlJetTable table = db.getTable(PDatExport.FORMAT_TABLE);
		ISqlJetCursor cursor = table.order("formatID");

		try {
			if (!cursor.eof()) {
				do {
					formatList_ID.add(cursor.getInteger("formatID"));
					formatList_TypeID.add(cursor.getInteger("typeID"));
					formatStructList.add(new FormatStruct(new Color((int)(cursor.getInteger("textColor"))), new Color((int)(cursor.getInteger("highlightColor"))), cursor.getString("description")));

				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}

		//less-than formats
		table = db.getTable(PDatExport.LESS_THAN_FORMAT_TABLE);
		cursor = table.order("typeID");
		try {
			if (!cursor.eof()) {
				do {
					long formatID = cursor.getInteger("formatID");
					int parentIndex = formatList_ID.indexOf(formatID);
					double val1 = cursor.getFloat("value1");

					EntityType eType = entityTypeList.get(entityTypeList_ID
							.indexOf(formatList_TypeID.get(parentIndex)));

					Format format = new LessThanFormat(0,0,val1,formatStructList.get(parentIndex).textColor,formatStructList.get(parentIndex).highlightColor,formatStructList.get(parentIndex).description);
					eType.getFormatting().add(format);

				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
		
		//greater-than formats
				table = db.getTable(PDatExport.GREATER_THAN_FORMAT_TABLE);
				cursor = table.order("typeID");
				try {
					if (!cursor.eof()) {
						do {
							long formatID = cursor.getInteger("formatID");
							int parentIndex = formatList_ID.indexOf(formatID);
							double val1 = cursor.getFloat("value1");

							EntityType eType = entityTypeList.get(entityTypeList_ID
									.indexOf(formatList_TypeID.get(parentIndex)));

							Format format = new GreaterThanFormat(0,0,val1,formatStructList.get(parentIndex).textColor,formatStructList.get(parentIndex).highlightColor,formatStructList.get(parentIndex).description);
							eType.getFormatting().add(format);

						} while (cursor.next());
					}
				} finally {
					cursor.close();
				}
				
				//between formats
				table = db.getTable(PDatExport.GREATER_THAN_FORMAT_TABLE);
				cursor = table.order("typeID");
				try {
					if (!cursor.eof()) {
						do {
							long formatID = cursor.getInteger("formatID");
							int parentIndex = formatList_ID.indexOf(formatID);
							double val1 = cursor.getFloat("value1");
							double val2 = cursor.getFloat("value2");

							EntityType eType = entityTypeList.get(entityTypeList_ID
									.indexOf(formatList_TypeID.get(parentIndex)));

							Format format = new BetweenFormat(0, val1,val2,formatStructList.get(parentIndex).textColor,formatStructList.get(parentIndex).highlightColor,formatStructList.get(parentIndex).description);
							eType.getFormatting().add(format);

						} while (cursor.next());
					}
				} finally {
					cursor.close();
				}
		
		
	}
	
	private class FormatStruct{
		public FormatStruct(Color textColor, Color highlightColor,
				String description) {
			super();
			this.textColor = textColor;
			this.highlightColor = highlightColor;
			this.description = description;
		}
		Color textColor;
		Color highlightColor;
		String description;
	}
}
