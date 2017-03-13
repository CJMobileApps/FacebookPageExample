# FacebookPageExample

Displays data into Android app from a Facebook page

YOU MUST ADD A FACEBOOK PAGE KEY TO THE com.cjfreelancing.facebookexample.utility.url public static String key.

Builds an application that displays the Photo Posts for the Facebook Fan Page, Dwayne's Auto and Transmission Repair Garage. The Photo Posts are sorted in reverse chronological order. The presentation is in a list the text, followed by a thumbnail of the image, the absolute timestamp, and an action that allows for Liking the photo. 15 Photo Posts are requested and presented at a time. The ability to load older Photo Posts is automatically triggered when a user scrolls to the bottom of the List.

Clicking on the thumbnail image in the cell should presents a Post Detail view which contains the same content as the cell along with a higher resolution image. The Post Detail view is a descendant of the List (i.e. a user should be able to navigate from the Post Detail back to the List). 

Future support for Facebook Reactions is also added, so clicking on the Like icon/text that is shown in the List cell will present a modal with a set of possible Facebook Like options. Clicking on any of the Like options will POST back to the Facebook API as the generic Like action. There is no need to Like from the Post Detail view.

