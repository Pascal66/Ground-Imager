package com.test.tango;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public abstract class FoiResponse implements Parcelable {
	public FoiRequest.Type mType;
	public String mId;
	public static final Creator<FoiResponse> CREATOR = new Creator<FoiResponse>() {
		public FoiResponse createFromParcel(Parcel in) {
			FoiRequest.Type type = FoiRequest.Type.fromInt(in.readInt());
			if (type == null) {
				return null;
			} else {
				String id = in.readString();
				if (id == null) {
					return null;
				} else {
					FoiResponse result = null;
					switch(type) {
						case CREATE:
							result = new FoiResponse.Create();
							break;
						case LOAD:
							result = new FoiResponse.Load();
							break;
						case DELETE:
							result = new FoiResponse.Delete();
							break;
						default:
							return null;
					}

					((FoiResponse)result).mType = type;
					((FoiResponse)result).mId = id;
					((FoiResponse)result).parcelRead(in);
					return (FoiResponse)result;
				}
			}
		}

		public FoiResponse[] newArray(int size) {
			return new FoiResponse[size];
		}
	};

	protected abstract void parcelRead(Parcel var1);

	protected abstract void parcelWrite(Parcel var1);

	private FoiResponse(FoiRequest.Type type) {
		this.mType = FoiRequest.Type.INVALID;
		this.mId = "";
		this.mType = type;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.mType.ordinal());
		dest.writeString(this.mId);
		this.parcelWrite(dest);
	}

	public static class Delete extends FoiResponse {
		public int[] mStatuses = null;
		public String[] mFrameIds = null;

		public Delete() {
			super(FoiRequest.Type.DELETE/*, null*/);
		}

		public boolean equals(Object obj) {
			if (obj != null && obj.getClass() == FoiResponse.Delete.class) {
				FoiResponse.Delete other = (FoiResponse.Delete)obj;
				boolean var10000;
				if (this.mType == other.mType) {
					label24: {
						if (this.mId != null) {
							if (!this.mId.equals(other.mId)) {
								break label24;
							}
						} else if (other.mId != null) {
							break label24;
						}

						if (Arrays.equals(this.mStatuses, other.mStatuses) && Arrays.equals(this.mFrameIds, other.mFrameIds)) {
							var10000 = true;
							return var10000;
						}
					}
				}

				var10000 = false;
				return var10000;
			} else {
				return false;
			}
		}

		protected void parcelRead(Parcel in) {
			this.mStatuses = in.createIntArray();
			this.mFrameIds = in.createStringArray();
		}

		protected void parcelWrite(Parcel dest) {
			dest.writeIntArray(this.mStatuses);
			dest.writeStringArray(this.mFrameIds);
		}
	}

	public static class Load extends FoiResponse {
		public int[] mStatuses = null;
		public String[] mFrameIds = null;

		public Load() {
			super(FoiRequest.Type.LOAD/*, null*/);
		}

		public boolean equals(Object obj) {
			if (obj != null && obj.getClass() == FoiResponse.Load.class) {
				FoiResponse.Load other = (FoiResponse.Load)obj;
				boolean var10000;
				if (this.mType == other.mType) {
					label24: {
						if (this.mId != null) {
							if (!this.mId.equals(other.mId)) {
								break label24;
							}
						} else if (other.mId != null) {
							break label24;
						}

						if (Arrays.equals(this.mStatuses, other.mStatuses) && Arrays.equals(this.mFrameIds, other.mFrameIds)) {
							var10000 = true;
							return var10000;
						}
					}
				}

				var10000 = false;
				return var10000;
			} else {
				return false;
			}
		}

		protected void parcelRead(Parcel in) {
			this.mStatuses = in.createIntArray();
			this.mFrameIds = in.createStringArray();
		}

		protected void parcelWrite(Parcel dest) {
			dest.writeIntArray(this.mStatuses);
			dest.writeStringArray(this.mFrameIds);
		}
	}

	public static class Create extends FoiResponse {
		public int mStatus = 0;
		public String mFrameId = null;

		public Create() {
			super(FoiRequest.Type.CREATE/*, null*/);
		}

		public boolean equals(Object obj) {
			if (obj != null && obj.getClass() == FoiResponse.Create.class) {
				boolean var10000;
				label46: {
					FoiResponse.Create other = (FoiResponse.Create)obj;
					if (this.mType == other.mType) {
						label40: {
							if (this.mId != null) {
								if (!this.mId.equals(other.mId)) {
									break label40;
								}
							} else if (other.mId != null) {
								break label40;
							}

							if (this.mStatus == other.mStatus) {
								if (this.mFrameId != null) {
									if (this.mFrameId.equals(other.mFrameId)) {
										break label46;
									}
								} else if (other.mFrameId == null) {
									break label46;
								}
							}
						}
					}

					var10000 = false;
					return var10000;
				}

				var10000 = true;
				return var10000;
			} else {
				return false;
			}
		}

		protected void parcelRead(Parcel in) {
			this.mStatus = in.readInt();
			this.mFrameId = in.readString();
		}

		protected void parcelWrite(Parcel dest) {
			dest.writeInt(this.mStatus);
			dest.writeString(this.mFrameId);
		}
	}
}
