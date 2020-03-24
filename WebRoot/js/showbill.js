function ToBill(billcode){
		var bc=billcode.substring(0,2);
	 switch(bc){
		case "TT":popWin("../warehouse/takeTicketDetailAction.do?ID="+billcode,900,600); break;
		case "SO":popWin("../sales/saleOrderDetailAction.do?ID="+billcode,900,600); break;
		case "PD":popWin("../sales/peddleOrderDetailAction.do?ID="+billcode,900,600); break;
		case "OM":popWin("../warehouse/stockAlterMoveDetailAction.do?ID="+billcode,900,600); break;	
		case "PC":popWin("../warehouse/productInterconvertDetailAction.do?ID="+billcode,900,600); break;
		case "RT":popWin("../sales/retailDetailAction.do?ID="+billcode,900,600); break;
		case "SR":popWin("../sales/withdrawDetailAction.do?ID="+billcode,900,600); break;
		case "WD":popWin("../aftersale/withdrawDetailAction.do?ID="+billcode,900,600); break;
		case "CM":popWin("../sales/saleTradesDetailAction.do?ID="+billcode,900,600);break;
		case "ST":popWin("../aftersale/saleTradesDetailAction.do?ID="+billcode,900,600);break;
		case "PW":popWin("../ditch/detailPlantWithdrawAction.do?ID="+billcode,900,600);break;
		case "PT":popWin("../aftersale/purchaseTradesDetailAction.do?ID="+billcode,900,600);break;
		case "SL":popWin("../warehouse/shipmentBillDetailAction.do?ID="+billcode,900,600);break;
		case "ML":popWin("../warehouse/stuffShipmentBillDetailAction.do?ID="+billcode,900,600); break;
		case "OL":popWin("../warehouse/otherShipmentBillDetailAction.do?ID="+billcode,900,600);break;
		case "PI":popWin("../warehouse/purchaseIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "PE":popWin("../warehouse/productIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "OI":popWin("../warehouse/otherIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "TM":popWin("../warehouse/stockMoveDetailAction.do?ID="+billcode,900,600); break;
		case "PY":popWin("../warehouse/otherIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "PK":popWin("../warehouse/otherShipmentBillDetailAction.do?ID="+billcode,900,600); break;
		case "DM":popWin("../ditch/detailSupplySaleMoveAction.do?ID="+billcode,900,600); break;
		case "SM":popWin("../warehouse/stockMoveDetailAction.do?ID="+billcode,900,600); break;
		case "OW":popWin("../ditch/detailOrganWithdrawAction.do?ID="+billcode,900,600); break;
		case "OT":popWin("../ditch/detailOrganTradesAction.do?ID="+billcode,900,600); break;
		case "IL":popWin("../finance/incomeLogDetailAction.do?ID="+billcode,900,600); break;
		case "PL":popWin("../finance/paymentLogDetailAction.do?ID="+billcode,900,600); break;
		case "PO":popWin("../purchase/purchaseBillDetailAction.do?ID="+billcode,900,600); break;
		case "DS":popWin("../warehouse/drawShipmentBillDetailAction.do?ID="+billcode,900,600); break;
		case "HS":popWin("../warehouse/harmShipmentBillDetailAction.do?ID="+billcode,900,600); break;
		case "QR":popWin("../machin/otherIncomeDetailAction.do?ID="+billcode,900,600); break;
		case "QC":popWin("../machin/otherShipmentBillDetailAction.do?ID="+billcode,900,600); break;
		case "BI":popWin("../warehouse/barcodeInventoryDetailAction.do?ID="+billcode,900,600); break;
		case "PS":popWin("../warehouse/packSeparateDetailAction.do?ID="+billcode,900,600); break;
		
		default:popWin("../sales/retailDetailAction.do?ID="+billcode,900,600);
	 }
	}
