package downloadportlet;

import java.io.File;
import java.io.Serializable;

public class LogFileObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private File file;
	private boolean isTarget=false;

	public LogFileObject() {
	}

	public LogFileObject(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isTarget() {
		return isTarget;
	}

	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}

}
