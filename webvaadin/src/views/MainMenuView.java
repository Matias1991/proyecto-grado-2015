package views;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Label;

public class MainMenuView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label label_Title;
	private Label label_Description;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public MainMenuView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		label_Title.setValue("<i><b>Bienvenido al Sistema de Gesti�n y Liquidaciones de Proyectos</b></i>");
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<p><b>MSMP (Meerkat SYS Manage Project)</b> es un producto de software que tiene la finalidad de llevar a cabo la <br />Gesti�n y Liquidaci�n de proyectos de la empresa, a trav�s de los distintos m�dulos proporcionados por el sistema.<p>");
		strBuilder.append("<p>Entre las principales funcionalidades ofrecidas por el sistema se encuentra el manejo de proyectos <br />y a partir de ellos poder realizar liquidaciones sobre los mismos.</p>");
		strBuilder.append("<p>Tambi�n cuenta con un m�dulo de <b>CMI(Control de Mando Integral)</b>, para que los socios de la empresa <br />puedan tener una perspectiva financiera real y medir cuantitativamente los resultados obtenidos.</p>");
		strBuilder.append("<p>En funci�n del m�dulo <b>CMI</b> se permite evaluar los datos de la empresa a lo largo de un cierto per�odo, bas�ndose <br />en la visualizaci�n mediante gr�ficas y tablas detalladas con los datos, para que luego esta informaci�n <br />pueda ser de ayuda para los socios de la empresa en la toma de desiciones.</p>");
		label_Description.setValue(strBuilder.toString());
		// TODO add user code here
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("880px");
		mainLayout.setHeight("501px");

		// top-level component properties
		setWidth("880px");
		setHeight("800px");
		
		// label_1
		label_Title = new Label();
		label_Title.setImmediate(false);
		label_Title.setWidth("-1px");
		label_Title.setHeight("-1px");
		label_Title.setContentMode(ContentMode.HTML);
		mainLayout.addComponent(label_Title, "top:62.0px;left: 180px;");
		
		// label_Description
		label_Description = new Label();
		label_Description.setImmediate(false);
		label_Description.setWidth("-1px");
		label_Description.setHeight("-1px");
		label_Description.setContentMode(ContentMode.HTML);
		mainLayout.addComponent(label_Description, "top:100.0px;left: 25px;");
		
		return mainLayout;
	}

}
