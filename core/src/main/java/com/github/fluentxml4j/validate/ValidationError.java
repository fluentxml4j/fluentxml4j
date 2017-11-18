package com.github.fluentxml4j.validate;

public class ValidationError
{
	private Severity severity;

	private String description;

	private String systemId;
	private String publicId;
	private int line;
	private int column;

	public ValidationError(Severity severity, String description, String systemId, String publicId, int line, int column)
	{
		this.severity = severity;
		this.description = description;
		this.systemId = systemId;
		this.publicId = publicId;
		this.line = line;
		this.column = column;
	}

	public Severity getSeverity()
	{
		return severity;
	}

	public String getPublicId()
	{
		return publicId;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public int getLine()
	{
		return line;
	}

	public int getColumn()
	{
		return column;
	}

	public String getDescription()
	{
		return description;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ValidationError that = (ValidationError) o;

		if (line != that.line) return false;
		if (column != that.column) return false;
		if (severity != that.severity) return false;
		if (!description.equals(that.description)) return false;
		if (systemId != null ? !systemId.equals(that.systemId) : that.systemId != null) return false;
		return publicId != null ? publicId.equals(that.publicId) : that.publicId == null;
	}

	@Override
	public int hashCode()
	{
		int result = severity.hashCode();
		result = 31 * result + description.hashCode();
		result = 31 * result + (systemId != null ? systemId.hashCode() : 0);
		result = 31 * result + (publicId != null ? publicId.hashCode() : 0);
		result = 31 * result + line;
		result = 31 * result + column;
		return result;
	}

	@Override
	public String toString()
	{
		return String.format("%s/%s[%d:%d]: %s %s",
				publicId,
				systemId,
				line,
				column,
				severity.name(),
				description);
	}
}
