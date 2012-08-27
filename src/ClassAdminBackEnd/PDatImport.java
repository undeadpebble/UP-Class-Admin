package ClassAdminBackEnd;

import java.awt.Color;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
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
		readTypes(db);
		readBordercases(db);
		readFormats(db);
		readEntities(db);
	}

	private void readTypes(SqlJetDb db) throws SqlJetException {
		db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
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

					Format format = new LessThanFormat(val1,formatStructList.get(parentIndex).textColor,formatStructList.get(parentIndex).highlightColor,formatStructList.get(parentIndex).description);
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

							Format format = new GreaterThanFormat(val1,formatStructList.get(parentIndex).textColor,formatStructList.get(parentIndex).highlightColor,formatStructList.get(parentIndex).description);
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

							Format format = new BetweenFormat(val1,val2,formatStructList.get(parentIndex).textColor,formatStructList.get(parentIndex).highlightColor,formatStructList.get(parentIndex).description);
							eType.getFormatting().add(format);

						} while (cursor.next());
					}
				} finally {
					cursor.close();
				}
		
		
	}
	
	private void readEntities(SqlJetDb db) throws SqlJetException{
		ISqlJetTable table = db.getTable(PDatExport.ENTITY_TABLE);
		ISqlJetCursor cursor = table.order("entityID");

		try {
			if (!cursor.eof()) {
				do {
					
					long entityID = cursor.getInteger("entityID");
					long parentID = cursor.getInteger("parentID");
					long typeID = cursor.getInteger("typeID");
					

					entityList_ID.add(entityID);
					EntityType eType = entityTypeList.get(entityTypeList_ID.indexOf(typeID));
					if (parentID == 0) {
						SuperEntity sEntity = new SuperEntity(eType, 0);
						entityList.add(sEntity);
						project.setHead(sEntity);
					} else {
						SuperEntity sEntity = new SuperEntity(eType,entityList.get(entityList_ID.indexOf(parentID)),0);
						entityList.add(sEntity);

					}

				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
		
		table = db.getTable(PDatExport.BEST_N_ENTITY_TABLE);
		cursor = table.order("entityID");

		try {
			if (!cursor.eof()) {
				do {
					
					long entityID = cursor.getInteger("entityID");
					long nValue = cursor.getInteger("N");

					
					int entityIndex = entityList_ID.indexOf(entityID);
					SuperEntity sEntity = entityList.get(entityIndex);
					
						SuperEntity newEntity = new BestNMarkEntity(sEntity, (int)nValue);
						entityList.set(entityIndex, newEntity);
					

				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
		
		
		table = db.getTable(PDatExport.MARK_ENTITY_TABLE);
		cursor = table.order("entityID");

		try {
			if (!cursor.eof()) {
				do {
					
					long entityID = cursor.getInteger("entityID");
					long mark = cursor.getInteger("mark");

					
					int entityIndex = entityList_ID.indexOf(entityID);
					SuperEntity sEntity = entityList.get(entityIndex);
					
						SuperEntity newEntity = new LeafMarkEntity(sEntity,mark);

						entityList.set(entityIndex, newEntity);
					

				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
		
		table = db.getTable(PDatExport.STRING_ENTITY_TABLE);
		cursor = table.order("entityID");

		try {
			if (!cursor.eof()) {
				do {
					
					long entityID = cursor.getInteger("entityID");
					String field = cursor.getString("field");

					
					int entityIndex = entityList_ID.indexOf(entityID);
					SuperEntity sEntity = entityList.get(entityIndex);
					
						SuperEntity newEntity = new LeafStringEntity(sEntity,field);

						entityList.set(entityIndex, newEntity);
					

				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
		
		table = db.getTable(PDatExport.IMG_ENTITY_TABLE);
		cursor = table.order("entityID");

		try {
			if (!cursor.eof()) {
				do {
					
					long entityID = cursor.getInteger("entityID");
					String field = cursor.getString("address");

					
					int entityIndex = entityList_ID.indexOf(entityID);
					SuperEntity sEntity = entityList.get(entityIndex);
					
						SuperEntity newEntity = new IMGEntity(sEntity,field);

						entityList.set(entityIndex, newEntity);
					

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
