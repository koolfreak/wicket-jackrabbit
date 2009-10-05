package org.dms.wicket.page;

import java.io.IOException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.dms.wicket.WicketApplication;
import org.dms.wicket.component.JcrDowloadLink;
import org.dms.wicket.page.service.FileStorageService;
import org.dms.wicket.repository.db.model.FileDescription;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 9 5 - 14:38:35
 */
public class HomePage extends JcrClientPage
{

    private static final long serialVersionUID = 1L;

    @SpringBean   private FileStorageService fileStorageService;

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public HomePage()
    {
	final String loanpath = WicketApplication.get().getDmsRepoPath()+ "loan";

	add(new Link<Void>("search")
	{
	    @Override
	    public void onClick()
	    {
		setRedirect(true);
		setResponsePage(SearchPage.class);
	    }
	});

	final FileUploadForm upload = new FileUploadForm("formup", loanpath);
	add(upload);

	final ListView<FileDescription> files = new ListView<FileDescription>("files", fileStorageService.loadAll())
	{

	    @Override
	    protected void populateItem(ListItem<FileDescription> item)
	    {
		final FileDescription fileDesc = item.getModelObject();
		item.setModel(new CompoundPropertyModel<FileDescription>(fileDesc));
		item.add(new Label("name"));
		item.add(new Label("lastModified"));
		/*item.add(new Link<CustomFileDescription>("delete", item
			.getModel())
		{

		    @Override
		    public void onClick()
		    {
			final CustomFileDescription filed = getModelObject();
			fileStorageService.delete(filed);

			setRedirect(true);
			setResponsePage(HomePage.class);
		    }
		});*/
		item.add(new JcrDowloadLink("download", item.getModelObject()));
	    }
	};

	add(files);
    }

    private class FileUploadForm extends Form<Void>
    {
	private FileUploadField fileUploadField;
	private String path;

	public FileUploadForm(String id, String path)
	{
	    super(id);
	    this.path = path;
	    setMultiPart(true);

	    // Add one file input field
	    add(fileUploadField = new FileUploadField("fileInput"));

	    setMaxSize(Bytes.megabytes(10));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void onSubmit()
	{
	    final FileUpload upload = fileUploadField.getFileUpload();
	    try
	    {
		if (upload != null)
		{
		    fileStorageService.save(path,upload.getClientFileName(),upload.getContentType(), upload.getInputStream());
		}
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    } catch (FileStorageException e)
	    {
		e.printStackTrace();
	    }

	    setRedirect(true);
	    setResponsePage(HomePage.class);
	}
    }
}
