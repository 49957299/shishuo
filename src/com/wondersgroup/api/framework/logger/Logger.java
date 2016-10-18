package com.wondersgroup.api.framework.logger;

import java.util.logging.Level;

public class Logger {
	
	private java.util.logging.Logger m_logger;

	public Logger(String loggerName) {
		this.m_logger = java.util.logging.Logger.getLogger(loggerName);
	}

	public String getName() {
		return this.m_logger.getName();
	}

	@Deprecated
	public void entering() {
		this.m_logger.fine(this.m_logger.getName());
	}

	public void entering(Object methodName) {
		StringBuilder sb = new StringBuilder("Enter ");
		if (methodName != null) {
			sb.append(methodName);
		} else {
			sb.append(" ");
		}
		this.m_logger.fine(sb.toString());
	}

	public void entering(Object[] parameters) {
		if ((parameters != null) && (parameters.length >= 1)) {
			StringBuilder sb = new StringBuilder("Enter ");
			sb.append(parameters[0]);

			for (int i = 1; i < parameters.length; i++) {
				sb.append(new StringBuilder().append(" Parameter[")
						.append(Integer.toString(i - 1)).append("]: ")
						.toString());
				if (parameters[i] != null) {
					sb.append(parameters[i].toString());
					sb.append("\r\n");
				} else {
					sb.append("null");
				}
			}
			this.m_logger.fine(sb.toString());
		}
	}

	@Deprecated
	public void exiting() {
		this.m_logger.fine(new StringBuilder().append("Exiting ")
				.append(this.m_logger.getName()).toString());
	}

	public void exiting(Object methodName) {
		String message = "Exit ";
		if (methodName != null) {
			message = new StringBuilder().append(message)
					.append(methodName.toString()).toString();
		} else {
			message = new StringBuilder().append(message).append("  ")
					.toString();
		}

		this.m_logger.fine(message);
	}

	public void exiting(Object[] results) {
		if ((results == null) || (results.length <= 0)) {
			return;
		}
		StringBuilder sb = new StringBuilder("Exit ");
		sb.append(results[0]);

		for (int i = 1; i < results.length; i++) {
			sb.append(new StringBuilder().append(" Result[")
					.append(Integer.toString(i - 1)).append("]: ").toString());
			if (results[i] != null) {
				sb.append(results[i]);
				sb.append("\r\n");
			} else {
				sb.append("null");
			}
		}
		this.m_logger.fine(sb.toString());
	}

	public boolean isLoggable(Level level) {
		return this.m_logger.isLoggable(level);
	}

	@Deprecated
	public boolean isLoggable(Level level, String unused) {
		return this.m_logger.isLoggable(level);
	}

	public boolean isEnteringLoggable() {
		return this.m_logger.isLoggable(Level.FINE);
	}

	public boolean isExitingLoggable() {
		return this.m_logger.isLoggable(Level.FINE);
	}

	public boolean isInfoLoggable() {
		return this.m_logger.isLoggable(Level.INFO);
	}

	public boolean isFineLoggable() {
		return this.m_logger.isLoggable(Level.FINE);
	}

	public boolean isFinerLoggable() {
		return this.m_logger.isLoggable(Level.FINER);
	}

	public boolean isFinestLoggable() {
		return this.m_logger.isLoggable(Level.FINEST);
	}

	public void admin(String msg) {
		this.m_logger.log(Level.SEVERE, msg);
	}

	public void admin(String msg, Throwable t) {
		this.m_logger.log(Level.SEVERE, msg, t);
	}

	public void severe(String errorCode, String msg) {
		this.m_logger.log(Level.SEVERE, new StringBuilder().append(errorCode)
				.append(msg).toString());
	}

	public void severe(String errorCode, String msg, Throwable t) {
		this.m_logger.log(Level.SEVERE, msg, t);
	}

	public void warning(String msg) {
		this.m_logger.warning(msg);
	}

	public void warning(String msg, Throwable t) {
		this.m_logger.log(Level.WARNING, msg, t);
	}

	public void info(String msg) {
		this.m_logger.info(msg);
	}

	public void info(String msg, Throwable t) {
		this.m_logger.log(Level.INFO, msg, t);
	}

	public void fine(String msg) {
		this.m_logger.fine(msg);
	}

	public void fine(String msg, Throwable t) {
		this.m_logger.log(Level.FINE, msg, t);
	}

	public void finer(String msg) {
		this.m_logger.finer(msg);
	}

	public void finer(String msg, Throwable t) {
		this.m_logger.log(Level.FINER, msg, t);
	}

	public void finest(String msg) {
		this.m_logger.finest(msg);
	}

	public void finest(String msg, Throwable t) {
		this.m_logger.log(Level.FINEST, msg, t);
	}

}
