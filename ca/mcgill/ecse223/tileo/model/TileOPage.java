/*

 */

import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;

public class TileOPage extends Jframe {

	// UI elements
	private JLabel errorMessage;

	// Board
	private JTextField fieldwidthTextField;
	private JTextField fieldlengthTextField;
	private JLabel BoardLabel;
	private JLabel Enterdim;
	private JButton displayBoard;
	private JLabel multLabel;

	// Player
	private JTextField playernumber;
	private JLabel player;
	private JTextField enterplayernumber;
	private JTextField selectplayer;
	private JButton selectstartposition;
	private JButton createplayer;
	private JComboBox<Integer> playerList;

	// Tile
	private JTextField Tile;
	private JtextField changetiletype;
	private JComboBox<String> tiletypeList;
	private JButton addTile;
	private JButton removeTile;
	private JButton applyChanges;

	// Connection
	private JComboBox<String> busList;
	private JLabel busLabel;
	private JComboBox<String> routeList;
	private JLabel routeLabel;
	private JDatePickerImpl assignmentDatePicker;
	private JLabel assignmentDateLabel;
	private JButton assignButton;
	// Deck
	private JComboBox<String> driverList;
	private JLabel driverLabel;
	private JComboBox<String> assignmentList;
	private JLabel assignmentLabel;
	private JComboBox<String> shiftList;
	private JLabel shiftLabel;
	private JButton scheduleButton;
	// daily overview
	private JDatePickerImpl overviewDatePicker;
	private JLabel overviewDateLabel;
	private JTable overviewTable;
	private JScrollPane overviewScrollPane;
	// bus route visualization
	private JComboBox<String> routeList2;
	private JButton upBusStopsButton;
	private JButton downBusStopsButton;
	private BusRouteVisualizer busRouteVisualizer;

}