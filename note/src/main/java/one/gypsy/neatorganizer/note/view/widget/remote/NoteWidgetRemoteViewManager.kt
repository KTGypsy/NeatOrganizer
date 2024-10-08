package one.gypsy.neatorganizer.note.view.widget.remote

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import one.gypsy.neatorganizer.core.widget.WidgetKeyring.MANAGED_WIDGET_ID_KEY
import one.gypsy.neatorganizer.core.widget.WidgetRemoteViewManager
import one.gypsy.neatorganizer.domain.dto.notes.TitledNoteWidgetEntryDto
import one.gypsy.neatorganizer.domain.interactors.notes.widget.DeleteNoteWidgetById
import one.gypsy.neatorganizer.domain.interactors.notes.widget.LoadTitledNoteWidget
import one.gypsy.neatorganizer.note.R
import one.gypsy.neatorganizer.note.view.widget.NoteWidgetKeyring.MANAGED_NOTE_ID_KEY
import one.gypsy.neatorganizer.note.view.widget.NoteWidgetKeyring.MANAGED_NOTE_INVALID_ID
import one.gypsy.neatorganizer.note.view.widget.management.NoteWidgetActivity

internal class NoteWidgetRemoteViewManager(
    private val context: Context,
    private val widgetManager: AppWidgetManager,
    private val loadTitledNoteWidgetUseCase: LoadTitledNoteWidget,
    private val deleteNoteWidgetUseCase: DeleteNoteWidgetById
) : WidgetRemoteViewManager {

    override fun updateWidget(appWidgetId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            loadTitledNoteWidgetUseCase.invoke(this, LoadTitledNoteWidget.Params(appWidgetId)) {
                it.either(
                    onFailure = { onLoadNoteWidgetFailure(appWidgetId) },
                    onSuccess = ::onLoadNoteWidgetSuccess
                )
            }
        }
    }

    private fun onLoadNoteWidgetFailure(appWidgetId: Int) {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_no_content).apply {
            setUpMissingNoteViews(appWidgetId)
        }
        widgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    // TODO it uses directly domain objects turn it into model objects
    private fun onLoadNoteWidgetSuccess(noteWidgetEntry: TitledNoteWidgetEntryDto) {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_note).apply {
            setUpLoadedNoteView(noteWidgetEntry)
        }
        widgetManager.updateAppWidget(noteWidgetEntry.appWidgetId, remoteViews)
    }

    private fun RemoteViews.setUpLoadedNoteView(noteWidgetEntry: TitledNoteWidgetEntryDto) {
        setOnClickPendingIntent(
            R.id.noteWidgetContainer,
            createNoteManageActivityIntent(
                noteWidgetEntry.appWidgetId,
                noteWidgetEntry.noteId
            )
        )
        setTextViewText(R.id.noteContent, noteWidgetEntry.noteContent)
        setInt(R.id.noteContent, "setBackgroundColor", noteWidgetEntry.widgetColor)
        setTextViewText(R.id.noteTitle, noteWidgetEntry.noteTitle)
    }

    private fun RemoteViews.setUpMissingNoteViews(widgetId: Int) {
        setOnClickPendingIntent(
            R.id.widgetContainer,
            createNoteManageActivityIntent(
                widgetId,
                MANAGED_NOTE_INVALID_ID
            )
        )
        setTextViewText(
            R.id.noContentMessage,
            context.getString(R.string.note_widget_missing_content_message)
        )
    }

    private fun createNoteManageActivityIntent(appwidgetId: Int, noteId: Long) =
        PendingIntent.getActivity(
            context,
            appwidgetId,
            createManageActivityIntent(appwidgetId, noteId),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun createManageActivityIntent(widgetId: Int, noteId: Long) =
        Intent(context, NoteWidgetActivity::class.java).apply {
            putExtra(MANAGED_NOTE_ID_KEY, noteId)
            putExtra(MANAGED_WIDGET_ID_KEY, widgetId)
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        }

    override fun deleteWidget(appWidgetId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteNoteWidgetUseCase.invoke(this, DeleteNoteWidgetById.Params(appWidgetId))
        }
    }
}
