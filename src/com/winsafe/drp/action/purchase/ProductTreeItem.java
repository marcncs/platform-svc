package com.winsafe.drp.action.purchase;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.ProductStruct;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ProductTreeItem {

  private Vector subItems;
  private String itemTitle;
  private String itemLink;

  private static String clickLink = null;
  /**
   * 用户点击某个页面时的响应参数中等级分类ID的参数名称
   */
  private static String productstructidArgumentName = null;

  public ProductTreeItem(String title, String link) {
    itemTitle = title;
    itemLink = link;
    subItems = new Vector();
  }

  /**
   * 获得本节点的标题
   * @return 本节点的标题
   */
  public String getTitle() {
    return itemTitle;
  }

  /**
   * 设置本节点的标题
   * @param title 本节点的标题
   */
  public void setTitle(String title) {
    itemTitle = title;
  }

  /**
   * 获得本节点的连接
   * @return 本节点的连接
   */
  public String getLink() {
    return itemLink;
  }

  /**
   * 设置本节点的连接
   * @param link 本节点的连接
   */
  public void setLink(String link) {
    itemLink = link;
  }

  /**
   * 获得属于本节点的子节点的数目
   * @return 本节点的子节点的数目
   */
  public int getSubItemNumber() {
    return subItems.size();
  }

  /**
   * 增加本节点以下的子节点
   * @param item 子节点
   */
  public void addSubItem(ProductTreeItem item) {
    subItems.add(item);
  }

  /**
   * 获得本节点以下的子节点
   * @return 本节点以下的子节点
   */
  public Iterator getSubItems() {
    return subItems.iterator();
  }

  /**
   * 得到某树的HTML Java Script代码
   * @param tree 树的Vector结构
   * @return 返回树的HTML Java Script代码
   */
  public static String genTreeHTMLCode(Vector tree) {
    ProductTreeItem item;
    Iterator iterator;
    StringBuffer code;
    if (tree == null) {
      return null;
    }
    code = new StringBuffer();
    code.append("<SCRIPT language=JavaScript>\n");
    code.append("var TREE_ITEMS = [\n");
    iterator = tree.iterator();
    while (iterator.hasNext()) {
      item = (ProductTreeItem) iterator.next();
      code.append("['");
      
      if (item.getTitle() != null) {
        code.append(item.getTitle());
      }
      else {
        code.append("No Name");
      }
      code.append("', ");
      
      if (item.getLink() != null) {
        code.append("'");
        code.append(item.getLink());
        code.append("'");
      }
      else {
        code.append("0");
      }
      code.append(", ");
      code.append(genSubTreeHTMLCode(item));
      code.append(']');
      if (iterator.hasNext()) {
        code.append(", ");
      }
    }
    code.append("\n];");
    code.append("\n</SCRIPT>");
    return code.toString();
  }

  /**
   * 产生子树的HTML Java Script代码
   * @param item 本Item值
   * @return 返回HTML Java Script代码
   */
  private static String genSubTreeHTMLCode(ProductTreeItem item) {
    int i, subItemNum;
    Iterator iterator;
    ProductTreeItem subItem;
    StringBuffer codeBuff;
    if (item == null) {
      return null;
    }
    subItemNum = item.getSubItemNumber();
    iterator = item.getSubItems();
    codeBuff = new StringBuffer();
    while (iterator.hasNext()) {
      subItem = (ProductTreeItem) iterator.next();
      codeBuff.append("['");
      
      if (subItem.getTitle() != null) {
        codeBuff.append(subItem.getTitle());
      }
      else {
        codeBuff.append("No Name");
      }
      codeBuff.append("', ");
      
      if (subItem.getLink() != null) {
        codeBuff.append("'");
        codeBuff.append(subItem.getLink());
        codeBuff.append("'");
      }
      else {
        codeBuff.append("0");
      }
      codeBuff.append("\n");
      if (item.getSubItemNumber() > 0) {
        codeBuff.append(", ");
        codeBuff.append(genSubTreeHTMLCode(subItem));
      }
      codeBuff.append("]");
      if (iterator.hasNext()) {
        codeBuff.append(", ");
      }
    }
    return codeBuff.toString();
  }

  /**
   * 直接产生类型数, 只要调用此函数就可以了
   * @return 类型数的Vector
   */
  public static Vector getProductTree(String linkAddSubTree,
                                      String productstructIdArgName) throws Exception{
    Vector tree;
    clickLink = linkAddSubTree;
    productstructidArgumentName = productstructIdArgName;
    AppProductStruct appProductStruct = new AppProductStruct();
    List cateList = null;
    // String active = null;
    int parentId = 0;
    cateList = appProductStruct.getProductStructChild(parentId);
    if (cateList != null) {
      tree = new Vector();
      Iterator tempIterator = cateList.iterator();
      while (tempIterator.hasNext()) {
        ProductStruct productstruct = (ProductStruct) tempIterator.next();
        ProductTreeItem tempCatTrItem = getProductSubTree(productstruct.getId());
        if (tempCatTrItem != null) {
          tree.add(tempCatTrItem);
        }
      }
      clickLink = null;
      productstructidArgumentName = null;
      return tree;
    }
    else {
      clickLink = null;
      productstructidArgumentName = null;
      return null;
    }
  }

  /**
   * 获得某一个产品类型树下面的子树, 本函数采用递归调用
   * @param typeId 类型ID
   * @return 返回这棵子树
   */

  private static ProductTreeItem getProductSubTree(Long id) throws Exception {
    ProductStruct productstructChild;
    ProductTreeItem item = null;
    StringBuffer linkBuff = new StringBuffer();
    AppProductStruct appProductStruct = new AppProductStruct();
    String active = null;
    if (clickLink != null && clickLink.length() > 0) {
      linkBuff = new StringBuffer();
      linkBuff.append(clickLink);
      linkBuff.append("?");
      linkBuff.append(productstructidArgumentName);
      linkBuff.append("=");
      linkBuff.append(id.longValue());
    }
   // item = new ProductTreeItem(appProductStruct.getProductStructById(id).
                               //getSortname(), linkBuff.toString());
    List productstructListChild = appProductStruct.getProductStructChild( (int)
        id.longValue());
    if (productstructListChild.size() != 0) {
      Iterator iteratorChild = productstructListChild.iterator();
      while (iteratorChild.hasNext()) {
        productstructChild = (ProductStruct) iteratorChild.next();
        ProductTreeItem subItem = getProductSubTree(productstructChild.getId());
        item.addSubItem(subItem);
      }
    }
    return item;
  }

  /**
   * 直接产生类型数, 并在每个ITEM中做ONCLICK的JAVASCRIPT事件处理
   * @return 类型数的Vector
   */
  public static Vector getProductTreeByOnclick(String linkAddSubTree,
                                               String productstructIdArgName) throws Exception{
    Vector tree;
    clickLink = linkAddSubTree;
    productstructidArgumentName = productstructIdArgName;
    AppProductStruct appProductStruct = new AppProductStruct();
    List cateList = null;
    String active = null;
    int parentId = 0;
    cateList = appProductStruct.getProductStructChild(parentId);
    if (cateList != null) {
      tree = new Vector();
      Iterator tempIterator = cateList.iterator();
      while (tempIterator.hasNext()) {
        ProductStruct productstruct = (ProductStruct) tempIterator.next();
        ProductTreeItem tempCatTrItem = getProductSubTreeByOnclick(
            productstruct.getId());
        if (tempCatTrItem != null) {
          tree.add(tempCatTrItem);
        }
      }
      clickLink = null;
      productstructidArgumentName = null;
      return tree;
    }
    else {
      clickLink = null;
      productstructidArgumentName = null;
      return null;
    }
  }

  /**
   * 获得某一个产品类型树下面的子树, 本函数采用递归调用
   * @param typeId 类型ID
   * @return 返回这棵子树
   */

  /**
   * 获得某一个产品类型树下面的子树, 本函数采用递归调用
   * @param typeId 类型ID
   * @return 返回这棵子树
   */

  private static ProductTreeItem getProductSubTreeByOnclick(Long id)throws Exception
  {
    ProductStruct productstructChild;
    ProductTreeItem item = null;
    StringBuffer linkBuff = new StringBuffer();
    AppProductStruct appProductStruct = new AppProductStruct();
    String active = null;
    if (clickLink != null && clickLink.length()>0 ){
         linkBuff = new StringBuffer();
         linkBuff.append(clickLink);
         linkBuff.append(id.longValue());
         linkBuff.append(");");
    }
   // item = new ProductTreeItem(appProductStruct.getProductStructById(id).getSortname(), linkBuff.toString());
    List productstructListChild = appProductStruct.getProductStructChild((int)id.longValue());
    if(productstructListChild.size() !=0){
      Iterator iteratorChild = productstructListChild.iterator();
      while (iteratorChild.hasNext()) {
        productstructChild = (ProductStruct) iteratorChild.next();
        ProductTreeItem subItem = getProductSubTreeByOnclick(productstructChild.getId());
        item.addSubItem(subItem);
      }
    }
    return item;
    }

}
