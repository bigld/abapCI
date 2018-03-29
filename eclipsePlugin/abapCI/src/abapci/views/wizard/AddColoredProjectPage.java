package abapci.views.wizard;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.sap.adt.project.AdtCoreProjectServiceFactory;
import com.sap.adt.tools.core.internal.AbapProjectService;

import abapci.domain.ColoredProject;
import abapci.domain.UiColor;
import abapci.presenter.ColoredProjectsPresenter;

 
public class AddColoredProjectPage extends Dialog {
 
	ColoredProjectsPresenter presenter; 
	Combo comboColoredProject; 
	Combo comboColor; 
	
     public AddColoredProjectPage(Shell parentShell, ColoredProjectsPresenter presenter) {
    	 super(parentShell);
    	 this.presenter = presenter; 
    	 parentShell.setText("Add a colored ABAP project");
     }
    
     @Override
     protected Control createDialogArea(Composite parent) {
         Composite container = (Composite) super.createDialogArea(parent);
         
         
         Label coloredProjectLabel = new Label(container, SWT.READ_ONLY);
         coloredProjectLabel.setText("Project:");
         coloredProjectLabel.setToolTipText("Select a ABAP project");
         coloredProjectLabel.setBounds(10,50,25,65);
         comboColoredProject = new Combo(container, SWT.READ_ONLY); 
         comboColoredProject.pack(); 
         comboColoredProject.setItems(getAbapProjectsArray());
         
         Label colorLabel = new Label(container, SWT.READ_ONLY);
         colorLabel.setToolTipText("Select a predefined color");
         colorLabel.setText("Color:");
         colorLabel.pack(); 

         comboColor = new Combo(container, SWT.READ_ONLY); 
         comboColor.pack(); 
         comboColor.setItems(getElementsOfEnum(UiColor.class));
         
         return container;
     }
     
     private String[] getAbapProjectsArray() {
 	     ArrayList<String> abapProjectnames = new ArrayList<>(); 
    	 IProject[] availableProjects = AdtCoreProjectServiceFactory.createCoreProjectService().getAvailableAdtCoreProjects();         
 	    
 	     for (IProject project : availableProjects)
         {
         	if (AbapProjectService.getInstance().isAbapProject(project))  
             {
         		abapProjectnames.add(project.getName()); 
         	}
         }
 	     abapProjectnames.add("Testproject"); 
 	     return abapProjectnames.toArray(new String[0]); 
	}
     
    public static String[] getElementsOfEnum(Class<? extends Enum<?>> e) {
    	    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

	@Override
     protected void okPressed() {
         presenter.addColoredProject(new ColoredProject(comboColoredProject.getText(), UiColor.valueOf(comboColor.getText())));
         presenter.setViewerInput();
         super.okPressed();
     }
}