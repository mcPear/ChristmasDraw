import {UserDto} from "./user.dto";

export interface UserIncludeDto extends UserDto{
  includeInFutureDraw: boolean;
  includedInLastDraw: Boolean;
}