package ClassAdminBackEnd;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import javax.activity.InvalidActivityException;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class EntityType {
	private String name;
	private LinkedList<Format> formatting;
	private LinkedList<BorderCase> borderCasing;
	private LinkedList<SuperEntity> entityList;
	private EntityType parentEntitytype;
	private LinkedList<EntityType> subEntityType;
	private Boolean isTextField = false;
	private Boolean isRule = false;
	private Date date;
	private Double defaultWeight;
	private long ID;
	private double maxValue = 100;
	private boolean isImg = false;
	private int N = 0;

	public static int WEIGHTED_AVERAGE_TYPE = 0;
	public static int SUM_TYPE = 1;
	public static int BEST_N_TYPE = 2;
	public static int TEXT_TYPE = 3;
	public static int MIXED = 4;

	/**
	 * @return
	 */
	public Boolean getIsRule() {
		return isRule;
	}

	/**
	 * @param isRule
	 */
	public void setIsRule(Boolean isRule) {
		this.isRule = isRule;
	}

	/**
	 * @return
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxmark
	 */
	public void setMaxValue(double maxmark) {
		this.maxValue = maxmark;
	}

	/**
	 * 
	 */
	public void updateMaxValue() {
		double total = 0;
		for (int x = 0; x < this.getSubEntityType().size(); ++x) {
			total += this.getSubEntityType().get(x).getMaxValue();
		}
		this.setMaxValue(total);
	}

	/**
	 * @return
	 */
	public EntityType getParentEntitytype() {
		return parentEntitytype;
	}

	/**
	 * @param parentEntitytype
	 */
	public void setParentEntitytype(EntityType parentEntitytype) {
		this.parentEntitytype = parentEntitytype;
	}

	/**
	 * @return
	 */
	public LinkedList<EntityType> getSubEntityType() {
		if (this.subEntityType == null)
			this.subEntityType = new LinkedList<EntityType>();
		return subEntityType;
	}

	/**
	 * @return the iD
	 */
	/**
	 * @return
	 */
	public long getID() {
		return ID;
	}

	/**
	 * @param n
	 */
	public EntityType(String n) {
		name = n;
	}

	/**
	 * @param name
	 * @param fields
	 * @param visibleFields
	 * @param fieldDefaults
	 * @param formatting
	 * @param borderCasing
	 * @param entityList
	 * @param isTextField
	 * @param date
	 * @param isVisible
	 * @param defaultWeight
	 */
	public EntityType(String name, LinkedList<Format> formatting,
			LinkedList<BorderCase> borderCasing,
			LinkedList<SuperEntity> entityList, Boolean isTextField, Date date,
			Double defaultWeight) {
		this.name = name;
		this.formatting = formatting;
		this.borderCasing = borderCasing;
		this.entityList = entityList;
		this.setIsTextField(isTextField);
		this.date = date;
		this.defaultWeight = defaultWeight;
	}

	/**
	 * @param name
	 * @param parentEntitytype
	 * @param isTextField
	 * @param date
	 * @param defaultWeight
	 */
	public EntityType(String name, EntityType parentEntitytype,
			Boolean isTextField, Date date, Double defaultWeight) {
		this.name = name;
		this.parentEntitytype = parentEntitytype;
		if (parentEntitytype != null)
			parentEntitytype.getSubEntityType().add(this);
		this.setIsTextField(isTextField);
		this.date = date;
		this.defaultWeight = defaultWeight;
	}

	/**
	 * 
	 */
	public EntityType() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param formatting
	 *            the formatting to set
	 */
	public void setFormatting(LinkedList<Format> formatting) {
		this.formatting = formatting;
	}

	/**
	 * 
	 */
	public void clearFormatting() {
		this.formatting = null;
	}

	/**
	 * @param borderCasing
	 *            the borderCasing to set
	 */
	public void setBorderCasing(LinkedList<BorderCase> borderCasing) {
		this.borderCasing = borderCasing;
	}

	/**
	 * @param entityList
	 *            the entityList to set
	 */
	public void setEntityList(LinkedList<SuperEntity> entityList) {
		this.entityList = entityList;
	}

	/**
	 * @param subEntityType
	 *            the subEntityType to set
	 */
	public void setSubEntityType(LinkedList<EntityType> subEntityType) {
		this.subEntityType = subEntityType;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public LinkedList<Format> getFormatting() {
		if (formatting == null)
			formatting = new LinkedList<Format>();
		return formatting;
	}

	/**
	 * @return
	 */
	public LinkedList<BorderCase> getBorderCasing() {
		if (borderCasing == null)
			borderCasing = new LinkedList<BorderCase>();
		return borderCasing;
	}

	/**
	 * @return
	 */
	public LinkedList<SuperEntity> getEntityList() {
		if (entityList == null)
			entityList = new LinkedList<SuperEntity>();
		return entityList;
	}

	/**
	 * @return
	 */
	public Boolean getIsTextField() {
		return isTextField;
	}

	/**
	 * @param isTextField
	 */
	public void setIsTextField(Boolean isTextField) {
		this.isTextField = isTextField;
	}

	/**
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return
	 */
	public Double getDefaultWeight() {
		return defaultWeight;
	}

	/**
	 * @param defaultWeight
	 */
	public void setDefaultWeight(Double defaultWeight) {
		this.defaultWeight = defaultWeight;
	}

	/**
	 * @param db
	 * @param parentID
	 * @param idgen
	 * @throws SqlJetException
	 * was used to save to an sql database, now deprecated
	 */
	public void saveToDB(SqlJetDb db, Long parentID, PDatIDGenerator idgen)
			throws SqlJetException {
		db.beginTransaction(SqlJetTransactionMode.WRITE);

		// TODO
		ISqlJetTable table = db.getTable(PDatExport.ENTITY_TYPE_TABLE);
		// insert statements
		this.ID = idgen.getID();
		table.insert(this.ID, this.name, parentID, this.isTextField, this.date,
				this.defaultWeight);

		for (int x = 0; x < this.getBorderCasing().size(); ++x) {
			this.getBorderCasing().get(x).saveToDB(db, this.ID, idgen);
		}
		for (int x = 0; x < this.getFormatting().size(); ++x) {
			this.getFormatting().get(x).saveToDB(db, this.ID, idgen);
		}

		for (int x = 0; x < this.getSubEntityType().size(); ++x) {
			this.getSubEntityType().get(x).saveToDB(db, this.ID, idgen);
		}
	}

	public void changeParent(EntityType newParent) {
		boolean fail = false;
		for (int x = 0; x < this.getEntityList().size(); ++x) {
			try {
				this.getEntityList().get(x).changeParentTotype(newParent);
			} catch (InvalidActivityException e) {
				undoChange(x);
				x = this.getEntityList().size();
				fail = true;
			}

		}

		if (!fail) {
			this.getParentEntitytype().getSubEntityType().remove(this);
			this.setParentEntitytype(newParent);
			newParent.getSubEntityType().add(this);
		}
	}

	public void undoChange(int num) {
		for (int x = 0; x < num; ++x) {
			try {
				this.getEntityList().get(x).changeParentTotype(this);
			} catch (InvalidActivityException e) {

			}

		}
	}

	public String createTreeFromHead(LinkedList<EntityType> treeLinkedList) {
		treeLinkedList.add(this);

		if (this.getSubEntityType().size() > 0) {
			String str = "";
			str += "<branch>" + "<attribute name = \"name\" value= \""
					+ this.getName() + "\" />";
			for (int i = 0; i < this.getSubEntityType().size(); i++) {
				str += this.getSubEntityType().get(i)
						.createTreeFromHead(treeLinkedList);
			}
			str += "</branch>";
			return str;
		} else {
			String str = "";
			str += "<leaf>" + "<attribute name = \"name\" value= \""
					+ this.getName() + "\" />";
			str += "</leaf>";
			return str;
		}
	}

	public void populateTreeWithEntities() {
		for (int x = this.getParentEntitytype().getEntityList().size() - 1; x >= 0; --x) {
			SuperEntity parent = this.getParentEntitytype().getEntityList()
					.get(x);
			if (this.getIsRule()) {
				if (this.getIsTextField()) {

					new StringRuleEntity(this, parent, "");

				} else {
					new floatRuleEntity(this, parent);
				}
			} else {
				if (this.getIsTextField()) {

					if (this.getIsImg()) {
						new IMGEntity(this, parent, this.getName());
					} else
						new LeafStringEntity(this, parent, "#" + this.getName()
								+ "#");

				} else {
					if (N > 0)
						new BestNMarkEntity(this, parent);
					new LeafMarkEntity(this, parent, 0);
				}

			}

		}
	}

	public void removeDeletingChildren() {
		for (int x = 0; x < this.getEntityList().size(); ++x) {
			this.getEntityList().get(x).getParentEntity().getSubEntity()
					.remove(this.getEntityList().get(x));
		}
		this.getEntityList().clear();
		this.getParentEntitytype().getSubEntityType().remove(this);
		this.setParentEntitytype(null);
	}

	public void removeSavingChildren() {
		for (int x = 0; x < this.getEntityList().size(); ++x) {
			for (int y = 0; y < this.getEntityList().get(x).getSubEntity()
					.size(); ++y) {
				this.getEntityList().get(x).getParentEntity().getSubEntity()
						.add(this.getEntityList().get(x).getSubEntity().get(y));
				this.getEntityList()
						.get(x)
						.getSubEntity()
						.get(y)
						.setParentEntity(
								this.getEntityList().get(x).getParentEntity());
			}
		}
		for (int x = 0; x < this.getSubEntityType().size(); ++x) {
			this.getSubEntityType().get(x)
					.setParentEntitytype(this.getParentEntitytype());
			this.getParentEntitytype().getSubEntityType()
					.add(this.getSubEntityType().get(x));
		}
		removeDeletingChildren();
	}

	public double getWeight() {
		return defaultWeight;
	}

	public void updateEntity(String pName, Boolean pIsTextField, Date pDate,
			Double weight) {
		name = pName;
		isTextField = pIsTextField;
		date = pDate;
		defaultWeight = weight;

	}

	public void findRapidAssessment(
			LinkedList<RapidAssessmentContainerType> list) {

		for (int x = 0; x < this.getSubEntityType().size(); ++x) {
			this.getSubEntityType().get(x).findRapidAssessment(list);
		}
	}

	public void findEntities(LinkedList<EntityType> list) {
		for (int x = 0; x < this.getSubEntityType().size(); ++x) {
			this.getSubEntityType().get(x).findEntities(list);
		}
		if (this.getParentEntitytype() != null
				&& this.getParentEntitytype().getParentEntitytype() != null)
			list.add(this);

	}

	public int getEntityTypeClass() {
		int classtype = -1;
		for (int x = 0; x < this.getEntityList().size(); ++x) {
			Object o = this.getEntityList().get(x).getClass();
			if (o.equals(MarkEntity.class) || o.equals(LeafMarkEntity.class)) {
				if (classtype < 0 || classtype == this.WEIGHTED_AVERAGE_TYPE)
					classtype = this.WEIGHTED_AVERAGE_TYPE;
				else
					classtype = this.MIXED;
			} else if (o.equals(SumMarkEntity.class)) {
				if (classtype < 0 || classtype == this.SUM_TYPE)
					classtype = this.SUM_TYPE;
				else
					classtype = this.MIXED;
			} else if (o.equals(BestNMarkEntity.class)) {
				if (classtype < 0 || classtype == this.BEST_N_TYPE)
					classtype = this.BEST_N_TYPE;
				else
					classtype = this.MIXED;
			} else if (o.equals(StringEntity.class)
					|| o.equals(LeafStringEntity.class)) {
				if (classtype < 0 || classtype == this.TEXT_TYPE)
					classtype = this.TEXT_TYPE;
				else
					classtype = this.MIXED;
			}
		}
		return classtype;
	}

	public void setEntityTypeClass(int classType) {
		Object o = null;
		boolean text = this.getIsTextField();

		this.setIsTextField(false);
		switch (classType) {

		case 0:
			o = MarkEntity.class;
			break;
		case 1:
			o = SumMarkEntity.class;
			break;
		case 2:
			o = BestNMarkEntity.class;
			break;
		case 3:
			o = StringEntity.class;
			this.setIsTextField(true);
			break;

		default:
			break;
		}
		for (int x = 0; x < this.getEntityList().size(); ++x) {
			SuperEntity newE = null;
			if (!this.getEntityList().get(x).getClass().equals(o)) {
				if (o.equals(MarkEntity.class)
						|| o.equals(LeafMarkEntity.class)) {
					newE = new MarkEntity(this.getEntityList().get(x));
				} else if (o.equals(SumMarkEntity.class)) {
					newE = new SumMarkEntity(this.getEntityList().get(x));
				} else if (o.equals(BestNMarkEntity.class)) {
					newE = new BestNMarkEntity(this.getEntityList().get(x));
				} else if (o.equals(StringEntity.class)
						|| o.equals(LeafStringEntity.class)) {
					newE = new StringEntity(this.getEntityList().get(x), this
							.getEntityList().get(x).getField());
				}
				--x;
				if (text) {

					if (this.getIsTextField()) {

					} else {
						try {
							newE.setMark(Double.parseDouble(newE.getField()));
						} catch (NumberFormatException e) {
							newE.clearMark();
						}

					}
				} else {
					if (this.getIsTextField()) {
						/*
						 * try { newE.setField(String.valueOf(newE.getMark()));
						 * } catch (AbsentException e) { newE.setField("-"); }
						 */
					} else {

					}
				}
			}
		}
	}

	public boolean getIsImg() {
		return isImg;
	}

	public void setIsImg(boolean isImg) {
		this.isImg = isImg;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

}
